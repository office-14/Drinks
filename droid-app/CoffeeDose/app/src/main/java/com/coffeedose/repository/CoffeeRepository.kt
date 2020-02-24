package com.coffeedose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coffeedose.database.CoffeeDao
import com.coffeedose.domain.Coffee
import com.coffeedose.network.CoffeeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CoffeeRepository (private val coffeeDao: CoffeeDao) {

    val drinks: LiveData<List<Coffee>> = Transformations.map(coffeeDao.getAllDrinks()){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    suspend fun refreshDrinks(){
        try {
            withContext(Dispatchers.IO){
                val drinksResponse = CoffeeApi.retrofitService.getDrinksAsync().await()
                coffeeDao.refreshDrinks(drinksResponse.payload.map { it.toDataBaseModel() })
            }
        }
        catch (ex:Exception){
            Log.d("CoffeeRepository.refreshDrinks", ex.message)
        }
    }
}