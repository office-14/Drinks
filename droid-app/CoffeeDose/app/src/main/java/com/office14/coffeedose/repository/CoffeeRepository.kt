package com.office14.coffeedose.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.CoffeeDao
import com.office14.coffeedose.domain.Coffee
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.network.HttpExceptionEx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoffeeRepository @Inject constructor (private val coffeeDao: CoffeeDao, private val coffeeApi : CoffeeApiService) {

    val drinks: LiveData<List<Coffee>> = Transformations.map(coffeeDao.getAllDrinks()){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    suspend fun refreshDrinks(){
        /*try {*/
            withContext(Dispatchers.IO) {
                val drinksResponse = coffeeApi.getDrinksAsync().await()
                if (drinksResponse.hasError())
                    throw HttpExceptionEx(drinksResponse.getError())
                else
                    coffeeDao.refreshDrinks(drinksResponse.payload!!.map { it.toDataBaseModel() })
            }
        /*}
        catch (ex:Exception){
            throw ex
        }*/
    }
}