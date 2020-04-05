package com.office14.coffeedose.di

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.repository.AddinsRepository
import com.office14.coffeedose.repository.SizesRepository
import com.office14.coffeedose.viewmodels.SelectDoseAndAddinsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class SelectDoseModule {
    @Binds
    @IntoMap
    @ViewModelKey(SelectDoseAndAddinsViewModel::class)
    abstract fun bindSelectDoseAndAddinsViewModel(viewModel: SelectDoseAndAddinsViewModel): ViewModel

    @Module
    companion object {

        @Provides
        fun provideSizesRepository(database: CoffeeDatabase, apiService : CoffeeApiService) = SizesRepository(database.sizeDatabaseDao, apiService)

        @Provides
        fun provideAddinsRepository(database: CoffeeDatabase, apiService : CoffeeApiService) = AddinsRepository(database.addinsDatabaseDao, apiService)

    }
}