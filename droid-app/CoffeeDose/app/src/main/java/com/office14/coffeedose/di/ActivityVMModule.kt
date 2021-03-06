package com.office14.coffeedose.di

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.viewmodels.MenuInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(MenuInfoViewModel::class)
    abstract fun bindMenuInfoViewModel(viewModel: MenuInfoViewModel): ViewModel
}