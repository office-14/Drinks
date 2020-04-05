package com.office14.coffeedose.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.di.AssistedSavedStateViewModelFactory
import com.office14.coffeedose.domain.Addin
import com.office14.coffeedose.domain.CoffeeSize
import com.office14.coffeedose.domain.OrderDetail
import com.office14.coffeedose.extensions.mutableLiveData
import com.office14.coffeedose.network.HttpExceptionEx
import com.office14.coffeedose.repository.AddinsRepository
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.SizesRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectDoseAndAddinsViewModel @AssistedInject constructor(application: Application,
                                                               @Assisted private val savedStateHandle: SavedStateHandle, private val sizesRepository : SizesRepository,
                                                               private val addinsRepository : AddinsRepository,
                                                               private val orderDetailsRepository : OrderDetailsRepository) : AndroidViewModel(application) {



    var sizes = sizesRepository.getSizes(savedStateHandle.get<Int>("drinkId") ?: -1)

    val addins = addinsRepository.addins

    private val drinkId : Int = savedStateHandle.get<Int>("drinkId") ?: -1

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val addinsTotal = mutableLiveData(0)

    val count = mutableLiveData(1)

    var isRefreshing = mutableLiveData(false)

    var errorMessage : MutableLiveData<String?> = mutableLiveData(null)

    private val _navigateDrinks = MutableLiveData<Boolean>()

    val navigateDrinks : LiveData<Boolean>
        get() = _navigateDrinks

    init {
        refreshData()
    }

    fun getSummary() : LiveData<String>{
        var result = MediatorLiveData<String>()

        val update = {
            val portionPrice = (addinsTotal.value?:0) + if (selectedSize.value == null) 0 else selectedSize.value!!.price
            val countP = count.value!!

            result.value = "${portionPrice*countP} Р."
        }

        result.addSource(selectedSize) { update.invoke()}
        result.addSource(addinsTotal) { update.invoke()}
        result.addSource(count) { update.invoke()}

        return result

    }

    private var selectedItemIndex = mutableLiveData(-1)

    val selectedSize : LiveData<CoffeeSize?>
        get() = _selectedSize

    private var _selectedSize  = Transformations.map(selectedItemIndex){
        if (sizes.value == null)
            return@map null
        else return@map sizes.value!![selectedItemIndex.value!!]
    }


    fun refreshData(showRefresh:Boolean = false){
        viewModelScope.launch {
            try {

                if (showRefresh) isRefreshing.value = true

                sizesRepository.refreshSizes(drinkId)
                addinsRepository.refreshAddins()

                errorMessage.value = null
            }
            catch (responseEx: HttpExceptionEx){
                errorMessage.value = responseEx.error.title
            }
            catch (ex: java.lang.Exception){
                errorMessage.value = ex.message
            }
            finally {
                if (showRefresh) isRefreshing.value = false
            }
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

        addin.isSelected = isChecked
    }

    fun updateCount(newValue : Int){
        if (count.value!! != newValue) count.value = newValue
    }

    private fun getAddinsToString() : String? {
        if (addins.value?.size == 0) return null
        else return addins.value!!.filter { it.isSelected }.map { it.price }.joinToString()
    }

    fun addIntoOrderDetails(){
        viewModelScope.launch {
            try {
                orderDetailsRepository.insertNew( OrderDetail(
                    id = 0,
                    drinkId = drinkId,
                    sizeId = selectedSize.value!!.id,
                    addIns = addins.value?.filter { it.isSelected } ?: listOf(),
                    count = count.value!!,
                    orderId = null
                ))
                _navigateDrinks.value = true
            }
            catch (ex:Exception){
                Log.d("SelectDoseAndAddinsViewModel.saveOrderDetails",ex.message)
            }
        }
    }

    fun doneNavigating(){
        _navigateDrinks.value = false
    }

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<SelectDoseAndAddinsViewModel>
}