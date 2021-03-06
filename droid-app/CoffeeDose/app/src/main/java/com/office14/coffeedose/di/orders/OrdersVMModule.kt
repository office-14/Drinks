package com.office14.coffeedose.di.orders

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.di.ViewModelKey
import com.office14.coffeedose.viewmodels.OrderDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class OrdersVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(OrderDetailsViewModel::class)
    abstract fun bindOrderDetailsViewModel(viewModel: OrderDetailsViewModel): ViewModel
}