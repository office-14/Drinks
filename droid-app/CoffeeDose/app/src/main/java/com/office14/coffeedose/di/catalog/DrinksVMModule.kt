package com.office14.coffeedose.di.catalog

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.di.AssistedSavedStateViewModelFactory
import com.office14.coffeedose.di.ViewModelKey
import com.office14.coffeedose.viewmodels.DrinksViewModel
import com.office14.coffeedose.viewmodels.SelectDoseAndAddinsViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
//@AssistedModule
//@Module(includes=[AssistedInject_DrinksVMModule::class])
abstract class DrinksVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(DrinksViewModel::class)
    abstract fun bindDrinksViewModel(viewModel: DrinksViewModel): ViewModel

    /*@Binds
    @IntoMap
    @ViewModelKey(DrinksViewModel::class)
    abstract fun bindVMFactory(f: DrinksViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>*/
}