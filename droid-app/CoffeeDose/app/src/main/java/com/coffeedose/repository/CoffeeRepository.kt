package com.coffeedose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coffeedose.database.CoffeeDatabase
import com.coffeedose.database.asDomainModel
import com.coffeedose.domain.Coffee
import com.coffeedose.network.CoffeeApi
import com.coffeedose.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CoffeeRepository (private val database: CoffeeDatabase) {

    val drinks: LiveData<List<Coffee>> = Transformations.map(database.sleepDatabaseDao.getAllDrinks()){
        it.asDomainModel()
    }

    suspend fun refreshDrinks(){
        try {
            withContext(Dispatchers.IO){

                val drinksResponse = CoffeeApi.retrofitService.getDrinks().await()
                database.sleepDatabaseDao.insertAllDrinks(*drinksResponse.payload.asDatabaseModel().toTypedArray())
            }
        }
        catch (ex:Exception){
            Log.d("CoffeeRepository.refreshDrinks", ex.message)
        }
    }
}