package com.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.coffeedose.database.CoffeeDatabase
import com.coffeedose.domain.Coffee
import com.coffeedose.extensions.mutableLiveData
import com.coffeedose.network.HttpExceptionEx
import com.coffeedose.repository.CoffeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

class DrinksViewModel(application:Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = CoffeeDatabase.getInstance(application)
    private val coffeeRepository = CoffeeRepository(database.drinksDatabaseDao)

    private var selectedItemIndex = mutableLiveData(-1)

    // list of drinks
    val drinks = coffeeRepository.drinks

    var isRefreshing = mutableLiveData(false)

    var errorMessage : MutableLiveData<String?> = mutableLiveData(null)


    val selectedDrink : LiveData<Coffee?>
    get() = _selectedDrink

    private var _selectedDrink  = Transformations.map(selectedItemIndex){
        if (drinks.value?.isEmpty() != false)
            return@map null
        else return@map drinks.value!![selectedItemIndex.value!!]
    }

    init {
        refreshData()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refreshData( showRefresh: Boolean = false  ){
        viewModelScope.launch {
            try {

                if (showRefresh) isRefreshing.value = true

                coffeeRepository.refreshDrinks()

                errorMessage.value = null
            }
            catch (responseEx:HttpExceptionEx){
                errorMessage.value = responseEx.error.title
            }
            catch (ex:Exception){
                errorMessage.value = ex.message
            }
            finally {
                if (showRefresh) isRefreshing.value = false
            }
        }
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