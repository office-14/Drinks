package com.office14.coffeedose.di.catalog

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.di.AssistedSavedStateViewModelFactory
import com.office14.coffeedose.di.ViewModelKey
import com.office14.coffeedose.viewmodels.SelectDoseAndAddinsViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@AssistedModule
@Module(includes=[AssistedInject_SelectDoseVMModule::class])
abstract class SelectDoseVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(SelectDoseAndAddinsViewModel::class)
    abstract fun bindVMFactory(f: SelectDoseAndAddinsViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}

