package com.office14.coffeedose.repository

import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.SizeDao
import com.office14.coffeedose.network.CoffeeApi
import com.office14.coffeedose.network.HttpExceptionEx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SizesRepository (private val sizesDao: SizeDao) {

    fun getSizes(drinkId:Int) =  Transformations.map(sizesDao.getSizesByDrinkId(drinkId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }


    suspend fun refreshSizes(drinkId:Int){
        /*try {*/
            withContext(Dispatchers.IO){
                val sizesResponse = CoffeeApi.retrofitService.getSizesByDrinkIdAsync(drinkId).await()
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