package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.domain.Coffee
import com.office14.coffeedose.extensions.mutableLiveData
import com.office14.coffeedose.network.HttpExceptionEx
import com.office14.coffeedose.repository.CoffeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

class DrinksViewModel(application:Application) : AndroidViewModel(application) {

    private val notDefinedId = -1

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = CoffeeDatabase.getInstance(application)
    private val coffeeRepository = CoffeeRepository(database.drinksDatabaseDao)


    // list of drinks
    val drinks = coffeeRepository.drinks

    var isRefreshing = mutableLiveData(false)

    var errorMessage: MutableLiveData<String?> = mutableLiveData(null)

    private val _selectedId = mutableLiveData(notDefinedId)

    val selectedId: LiveData<Int>
        get() = _selectedId

    fun getDrinkName(): String {
        val coffee = drinks.value?.firstOrNull{ coffee -> coffee.id == _selectedId.value }
        return coffee?.name ?: "Not defined"
    }


    init {
        refreshData()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refreshData(showRefresh: Boolean = false) {
        viewModelScope.launch {
            try {

                if (showRefresh) isRefreshing.value = true

                coffeeRepository.refreshDrinks()

                errorMessage.value = null
            } catch (responseEx: HttpExceptionEx) {
                errorMessage.value = responseEx.error.title
            } catch (ex: Exception) {
                errorMessage.value = ex.message
            } finally {
                if (showRefresh) isRefreshing.value = false
            }
        }
    }

    fun doneNavigating() {
        _selectedId.value = notDefinedId
    }

    fun selectDrink(id: Int) {
        _selectedId.value = id
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
