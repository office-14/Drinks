package com.office14.coffeedose

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.office14.coffeedose.bindings.BindingComponent
import com.office14.coffeedose.di.AppComponent
import com.office14.coffeedose.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/*
    Override app class
 */

class CoffeeDoseApplication : DaggerApplication() {


    private lateinit var appComponent: AppComponent
            /*by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(this)
    }*/


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

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        //return DaggerAppComponent.builder().create(this)
        appComponent = DaggerAppComponent.factory().create(this)
        return appComponent
    }


}