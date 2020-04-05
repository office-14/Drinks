package com.office14.coffeedose.repository

import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.SizeDao
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.network.HttpExceptionEx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SizesRepository @Inject constructor (private val sizesDao: SizeDao, private val coffeeApi : CoffeeApiService) {

    fun getSizes(drinkId:Int) =  Transformations.map(sizesDao.getSizesByDrinkId(drinkId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }


    suspend fun refreshSizes(drinkId:Int){
        /*try {*/
            withContext(Dispatchers.IO){
                val sizesResponse = coffeeApi.getSizesByDrinkIdAsync(drinkId).await()
                if (sizesResponse.hasError())
                    throw HttpExceptionEx(sizesResponse.getError())
                else
                    sizesDao.refreshSizes(sizesResponse.payload!!.map { it.toDatabaseModel(drinkId) })
            }
        /*}
        catch (ex: Exception){
            Log.d("SizesRepository.refreshSizes", ex.message?:"")
        }*/
    }
}