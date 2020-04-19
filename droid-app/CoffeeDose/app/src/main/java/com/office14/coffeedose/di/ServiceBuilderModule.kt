package com.office14.coffeedose.di

import com.office14.coffeedose.services.FirebaseMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector




@Module
abstract class ServiceBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeFirebaseMessagingService(): FirebaseMessagingService
}