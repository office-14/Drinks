package com.coffeedose.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coffeedose.database.CoffeeDatabase
import com.coffeedose.database.asDomainModel
import com.coffeedose.domain.Coffee
import com.coffeedose.network.CoffeeApi
import com.coffeedose.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers

class CoffeeRepository (private val database: CoffeeDatabase) {

    val drinks: LiveData<List<Coffee>> = Transformations.map(database.sleepDatabaseDao.getAllDrinks()){
        it.asDomainModel()
    }

    suspend fun refreshDrinks(){
        with(Dispatchers.IO){
            val drinksReponse = CoffeeApi.retrofitService.getDrinks().await()

            if (!drinksReponse.message.isNullOrEmpty()){
                //TODO handle exception
            }
            else{
                //if (!drinksReponse.payload?.isEmpty()!!)
                var payload = drinksReponse.payload!!.asDatabaseModel()
                database.sleepDatabaseDao.insertAllDrinks(*payload.toTypedArray())
            }


        }
    }
}