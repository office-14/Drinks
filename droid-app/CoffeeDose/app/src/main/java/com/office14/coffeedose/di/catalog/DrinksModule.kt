package com.office14.coffeedose.di.catalog

import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.repository.CoffeeRepository
import dagger.Module
import dagger.Provides

@Module
abstract class DrinksModule {

    @CatalogScope
    @Module
    companion object {
        @CatalogScope
        @Provides
        fun provideCoffeeRepository(database: CoffeeDatabase, apiService: CoffeeApiService) =
            CoffeeRepository(database.drinksDatabaseDao, apiService)
    }
}