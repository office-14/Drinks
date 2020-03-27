package com.office14.coffeedose.di

import com.office14.coffeedose.ui.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun contributeCoffeeDoseActivity() : CoffeeDoseActivity

    /*@ContributesAndroidInjector(modules = [DrinksModule::class])
    abstract fun contributeDrinksFragment(): DrinksFragment

    @ContributesAndroidInjector(modules = [SelectDoseModule::class])
    abstract fun contributeSelectDoseAndAddinsFragment(): SelectDoseAndAddinsFragment

    @ContributesAndroidInjector(modules = [OrdersModule::class])
    abstract fun contributeOrderDetailsFragment(): OrderDetailsFragment

    @ContributesAndroidInjector(modules = [OrderAwaitingModule::class])
    abstract fun contributeOrderAwaitingFragment(): OrderAwaitingFragment*/

}