package com.office14.coffeedose.di

import com.office14.coffeedose.ui.OrderDetailsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(
    modules = [OrdersModule::class]
)
interface OrdersComponent : AndroidInjector<OrderDetailsFragment> {
    @Subcomponent.Factory
    interface Factory {
        fun create(): OrdersComponent
    }
}