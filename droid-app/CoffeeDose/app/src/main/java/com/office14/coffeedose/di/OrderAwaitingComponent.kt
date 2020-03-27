package com.office14.coffeedose.di

import com.office14.coffeedose.ui.OrderAwaitingFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [OrderAwaitingModule::class])
interface OrderAwaitingComponent : AndroidInjector<OrderAwaitingFragment> {
    @Subcomponent.Factory
    interface Factory {
        fun create(): OrderAwaitingComponent
    }
}