package com.office14.coffeedose.di

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.viewmodels.OrderAwaitingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class OrderAwaitingModule {
    @Binds
    @IntoMap
    @ViewModelKey(OrderAwaitingViewModel::class)
    abstract fun bindOrderAwaitingViewModel(viewModel: OrderAwaitingViewModel): ViewModel
}