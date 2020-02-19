package com.coffeedose

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.coffeedose.bindings.BindingComponent

/*
    Override app class
 */

class CoffeeDoseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(BindingComponent())
    }
}