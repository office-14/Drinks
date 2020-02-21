package com.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.coffeedose.database.CoffeeDatabase
import com.coffeedose.domain.Addin
import com.coffeedose.domain.Coffee
import com.coffeedose.domain.CoffeeSize
import com.coffeedose.extensions.mutableLiveData
import com.coffeedose.repository.AddinsRepository
import com.coffeedose.repository.SizesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SelectDoseAndAddinsViewModel(application: Application,var drinkId : Int) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val sizesRepository = SizesRepository(CoffeeDatabase.getInstance(application).sizeDatabaseDao)
    private val addinsRepository = AddinsRepository(CoffeeDatabase.getInstance(application).addinsDatabaseDao)

    private val addinsTotal = mutableLiveData(0)

    fun getTotal() : LiveData<Int?> {
        val result = MediatorLiveData<Int>()

        var sum = {
            result.value = (addinsTotal.value?:0) + (selectedSize.value?.price?:0)
        }

        result.addSource(addinsTotal){  sum.invoke() }
        result.addSource(selectedSize){  sum.invoke() }

        return result
    }

    private var selectedItemIndex = mutableLiveData(-1)

    val sizes = sizesRepository.getSizes(drinkId)
    val addins = addinsRepository.addins

    val selectedSize : LiveData<CoffeeSize?>
        get() = _selectedSize

    private var _selectedSize  = Transformations.map(selectedItemIndex){
        if (sizes.value?.isEmpty() != false)
            return@map null
        else return@map sizes.value!![selectedItemIndex.value!!]
    }


    init {
        viewModelScope.launch {
            sizesRepository.refreshSizes(drinkId)
            addinsRepository.refreshAddins()
        }
    }

    fun onSelectedSizeIndexChanged(newIndex : Int){
        if (newIndex != selectedItemIndex.value)
            selectedItemIndex.value = newIndex
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateTotalOnAddinCheck(addin : Addin, isChecked : Boolean){
        if (isChecked) addinsTotal.value = addinsTotal.value?.plus(addin.price)
        else addinsTotal.value = addinsTotal.value?.minus(addin.price)
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