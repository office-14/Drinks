package com.office14.coffeedose.di.catalog

import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.repository.AddinsRepository
import com.office14.coffeedose.repository.SizesRepository
import dagger.Module
import dagger.Provides

@Module
abstract class SelectDoseModule {

    @CatalogScope
    @Module
    companion object {
        @CatalogScope
        @Provides
        fun provideSizesRepository(database: CoffeeDatabase, apiService: CoffeeApiService) =
            SizesRepository(database.sizeDatabaseDao, apiService)

        @CatalogScope
        @Provides
        fun provideAddinsRepository(database: CoffeeDatabase, apiService: CoffeeApiService) =
            AddinsRepository(database.addinsDatabaseDao, apiService)
    }

}