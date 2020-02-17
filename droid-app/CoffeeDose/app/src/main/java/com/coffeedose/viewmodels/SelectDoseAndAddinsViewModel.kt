package com.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectDoseAndAddinsViewModel(application: Application,var drinkId : Int) : AndroidViewModel(application) {

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