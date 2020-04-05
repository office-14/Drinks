package com.office14.coffeedose.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.AddinDao
import com.office14.coffeedose.domain.Addin
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.network.HttpExceptionEx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddinsRepository @Inject constructor(private val addinsDatabaseDao: AddinDao, private val coffeeApi : CoffeeApiService) {

    val addins: LiveData<List<Addin>> = Transformations.map(addinsDatabaseDao.getAllAddins()){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    suspend fun refreshAddins(){
        /*try {*/
            withContext(Dispatchers.IO){
                val addinsResponse = coffeeApi.getAddinsAsync().await()
                if (addinsResponse.hasError())
                    throw HttpExceptionEx(addinsResponse.getError())
                else
                    addinsDatabaseDao.refreshAddins(addinsResponse.payload!!.map { it.toDataBaseModel() })
            }
        /*}
        catch (ex: Exception){
            Log.d("AddinsRepository.refreshAddins", ex.message?:"")
        }*/
    }
}