package com.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.coffeedose.database.CoffeeDatabase
import com.coffeedose.domain.Coffee
import com.coffeedose.extensions.mutableLiveData
import com.coffeedose.repository.CoffeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DrinksViewModel(application:Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = CoffeeDatabase.getInstance(application)
    private val coffeeRepository = CoffeeRepository(database.drinksDatabaseDao)

    private var selectedItemIndex = mutableLiveData(-1)

    // list of drinks
    val drinks = coffeeRepository.drinks


    val selectedDrink : LiveData<Coffee?>
    get() = _selectedDrink

    private var _selectedDrink  = Transformations.map(selectedItemIndex){
        if (drinks.value?.isEmpty() != false)
            return@map null
        else return@map drinks.value!![selectedItemIndex.value!!]
    }

    init {
        viewModelScope.launch {
            coffeeRepository.refreshDrinks()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onSelectedItemIndexChanged(newIndex : Int){
        if (newIndex != selectedItemIndex.value)
            selectedItemIndex.value = newIndex
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