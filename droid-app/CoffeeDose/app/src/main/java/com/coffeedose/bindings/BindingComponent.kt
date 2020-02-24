package com.coffeedose.bindings

import androidx.databinding.DataBindingComponent

class BindingComponent : DataBindingComponent {
    override fun getSpinnerBindings(): SpinnerBindings {
        return SpinnerBindings()
    }
}