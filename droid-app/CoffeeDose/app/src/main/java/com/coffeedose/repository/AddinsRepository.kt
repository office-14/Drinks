package com.coffeedose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coffeedose.database.AddinDao
import com.coffeedose.domain.Addin
import com.coffeedose.network.CoffeeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddinsRepository(private val addinsDatabaseDao: AddinDao) {

    val addins: LiveData<List<Addin>> = Transformations.map(addinsDatabaseDao.getAllAddins()){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    suspend fun refreshAddins(){
        try {
            withContext(Dispatchers.IO){
                val addinsResponse = CoffeeApi.retrofitService.getAddinsAsync().await()
                addinsDatabaseDao.insertAllAddins(*addinsResponse.payload.map { it.toDataBaseModel() }.toTypedArray())
            }
        }
        catch (ex: Exception){
            Log.d("AddinsRepository.refreshAddins", ex.message)
        }
    }
}