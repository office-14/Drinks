package com.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coffeedose.database.CoffeeDatabase
import com.coffeedose.repository.AddinsRepository
import com.coffeedose.repository.SizesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SelectDoseAndAddinsViewModel(application: Application,var drinkId : Int) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val sizesRepository = SizesRepository(CoffeeDatabase.getInstance(application).sizeDatabaseDao,drinkId)
    private val addinsRepository = AddinsRepository(CoffeeDatabase.getInstance(application).addinsDatabaseDao)

    val sizes = sizesRepository.sizes
    val addins = addinsRepository.addins


    init {
        viewModelScope.launch {
            sizesRepository.refreshSizes()
            addinsRepository.refreshAddins()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    class Factory(val app: Application, private var drinkId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectDoseAndAddinsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SelectDoseAndAddinsViewModel(app,drinkId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}