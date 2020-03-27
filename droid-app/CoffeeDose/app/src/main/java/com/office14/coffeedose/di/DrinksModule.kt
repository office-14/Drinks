package com.office14.coffeedose.di

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.di.ActivityBuildersModule_ContributeOrderDetailsFragment.OrderDetailsFragmentSubcomponent
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.repository.CoffeeRepository
import com.office14.coffeedose.ui.OrderDetailsFragment
import com.office14.coffeedose.viewmodels.DrinksViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class DrinksModule {

    @Binds
    @IntoMap
    @ViewModelKey(DrinksViewModel::class)
    abstract fun bindDrinksViewModel(viewModel: DrinksViewModel): ViewModel

    @Module
    companion object {
        @Provides
        fun provideCoffeeRepository(database: CoffeeDatabase, apiService: CoffeeApiService) =
            CoffeeRepository(database.drinksDatabaseDao, apiService)
    }

}