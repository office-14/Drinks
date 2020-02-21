package com.coffeedose

import android.app.Application
import android.content.Context
import androidx.databinding.DataBindingUtil
import com.coffeedose.bindings.BindingComponent

/*
    Override app class
 */

class CoffeeDoseApplication : Application() {

    companion object{
        private lateinit var instance : CoffeeDoseApplication

        fun applicationContext() : Context {
            return  instance.applicationContext
        }
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(BindingComponent())
    }
}