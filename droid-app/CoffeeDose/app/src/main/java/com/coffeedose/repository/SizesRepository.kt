package com.coffeedose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coffeedose.database.SizeDao
import com.coffeedose.domain.CoffeeSize
import com.coffeedose.network.CoffeeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class SizesRepository (private val sizesDao: SizeDao, private val drinkId:Int) {

    val sizes : LiveData<List<CoffeeSize>> =  Transformations.map(sizesDao.getSizesByDrinkId(drinkId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }


    suspend fun refreshSizes(){
        try {
            withContext(Dispatchers.IO){
                val sizesResponse = CoffeeApi.retrofitService.getSizesByDrinkIdAsync(drinkId).await()
                sizesDao.insertAllSizes(*sizesResponse.payload.map { it.toDatabaseModel(drinkId) }.toTypedArray())
            }
        }
        catch (ex: Exception){
            Log.d("SizesRepository.refreshSizes", ex.message)
        }
    }
}