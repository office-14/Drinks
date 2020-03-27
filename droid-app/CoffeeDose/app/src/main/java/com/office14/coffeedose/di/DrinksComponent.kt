package com.office14.coffeedose.di

import com.office14.coffeedose.ui.DrinksFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [DrinksModule::class])
interface DrinksComponent : AndroidInjector<DrinksFragment> {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DrinksComponent
    }
}