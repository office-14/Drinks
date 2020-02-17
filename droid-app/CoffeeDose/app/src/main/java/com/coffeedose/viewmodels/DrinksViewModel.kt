package com.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coffeedose.database.CoffeeDatabase
import com.coffeedose.repository.CoffeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DrinksViewModel(application:Application) : AndroidViewModel(application) {

    private var viewModelJob = SupervisorJob()

    private var viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var database = CoffeeDatabase.getInstance(application)
    private var coffeeRepository = CoffeeRepository(database)

    val drinks = coffeeRepository.drinks

    init {
        viewModelScope.launch {
            coffeeRepository.refreshDrinks()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DrinksViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DrinksViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}