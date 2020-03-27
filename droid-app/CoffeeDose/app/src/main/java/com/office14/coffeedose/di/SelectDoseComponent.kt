package com.office14.coffeedose.di

import com.office14.coffeedose.ui.SelectDoseAndAddinsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [SelectDoseModule::class])
interface SelectDoseComponent : AndroidInjector<SelectDoseAndAddinsFragment> {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SelectDoseComponent
    }
}