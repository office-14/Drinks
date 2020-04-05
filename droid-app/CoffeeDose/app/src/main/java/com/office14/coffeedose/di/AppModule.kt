package com.office14.coffeedose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.office14.coffeedose.CoffeeDoseApplication
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.repository.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Module
    companion object {

        @Singleton
        @Provides
        fun provideMessage() = "All fine"


        @Singleton
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Singleton
        @Provides
        fun provideMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        @Singleton
        @Provides
        fun provideRetrofit(moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(PreferencesRepository.getBaseUrl())
                .build()
        }

        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit) = retrofit.create(CoffeeApiService::class.java)

        @Singleton
        @Provides
        fun provideDataBase(context: Context) : CoffeeDatabase {
            return Room.databaseBuilder(
                context,
                CoffeeDatabase::class.java,
                "drinks_database"
            ).fallbackToDestructiveMigration().build()
        }

        @Singleton
        @Provides
        fun provideOrderDetailsRepository(database: CoffeeDatabase) =
            OrderDetailsRepository(database.orderDetailsDatabaseDao)

        @Singleton
        @Provides
        fun provideOrdersRepository(database: CoffeeDatabase, apiService: CoffeeApiService) =
            OrdersRepository(
                database.ordersDatabaseDao,
                database.ordersQueueDatabaseDao,
                apiService
            )

    }
}