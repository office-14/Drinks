package com.coffeedose.bindings

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.coffeedose.domain.Coffee
import com.coffeedose.extensions.ItemSelectedListener
import com.coffeedose.extensions.setSpinnerEntries
import com.coffeedose.extensions.setSpinnerItemSelectedListener
import com.coffeedose.extensions.setSpinnerValue

class SpinnerBindings {

    @BindingAdapter("drinks")
    fun Spinner.setEntries(entries: List<Coffee>?) {
        setSpinnerEntries(entries)
    }

    @BindingAdapter("onItemSelected")
    fun Spinner.setOnItemSelectedListener(itemSelectedListener: ItemSelectedListener?) {
        setSpinnerItemSelectedListener(itemSelectedListener)
    }

    @BindingAdapter("newValue")
    fun Spinner.setNewValue(newValue: Any?) {
        setSpinnerValue(newValue)
    }
}