package com.coffeedose.bindings

import android.view.View
import androidx.databinding.BindingConversion

object BindingConverters {

    @BindingConversion
    @JvmStatic
    fun booleanToVisibility(visible: Boolean): Int {
        return if (!visible) View.GONE else View.VISIBLE
    }
}