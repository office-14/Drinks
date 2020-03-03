package com.office14.coffeedose.bindings

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.office14.coffeedose.domain.Coffee
import com.office14.coffeedose.extensions.ItemSelectedListener
import com.office14.coffeedose.extensions.setSpinnerEntries
import com.office14.coffeedose.extensions.setSpinnerItemSelectedListener
import com.office14.coffeedose.extensions.setSpinnerValue

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