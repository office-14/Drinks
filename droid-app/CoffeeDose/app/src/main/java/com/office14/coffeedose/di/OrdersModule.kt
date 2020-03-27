package com.office14.coffeedose.di

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.viewmodels.OrderDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class OrdersModule {
    @Binds
    @IntoMap
    @ViewModelKey(OrderDetailsViewModel::class)
    abstract fun bindOrderDetailsViewModel(viewModel: OrderDetailsViewModel): ViewModel
}