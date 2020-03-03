package com.office14.coffeedose.bindings

import androidx.databinding.DataBindingComponent

class BindingComponent : DataBindingComponent {
    override fun getSpinnerBindings(): SpinnerBindings {
        return SpinnerBindings()
    }
}