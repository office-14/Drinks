package com.office14.coffeedose.di

import com.office14.coffeedose.di.catalog.*
import com.office14.coffeedose.di.orders.OrdersVMModule
import com.office14.coffeedose.di.orders.OrdersScope
import com.office14.coffeedose.di.ordersawaiting.OrderAwaitingModule
import com.office14.coffeedose.di.ordersawaiting.OrdersAwaitingScope
import com.office14.coffeedose.ui.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [ActivityVMModule::class, ActivityBuildersSubModule::class])
    abstract fun contributeCoffeeDoseActivity() : CoffeeDoseActivity

}


@Module
abstract class ActivityBuildersSubModule {

    @CatalogScope
    @ContributesAndroidInjector(modules = [DrinksVMModule::class, DrinksModule::class])
    abstract fun contributeDrinksFragment(): DrinksFragment

    @CatalogScope
    @ContributesAndroidInjector(modules = [SelectDoseVMModule::class, SelectDoseModule::class])
    abstract fun contributeSelectDoseAndAddinsFragment(): SelectDoseAndAddinsFragment

    @OrdersScope
    @ContributesAndroidInjector(modules = [OrdersVMModule::class])
    abstract fun contributeOrderDetailsFragment(): OrderDetailsFragment

    @OrdersAwaitingScope
    @ContributesAndroidInjector(modules = [OrderAwaitingModule::class])
    abstract fun contributeOrderAwaitingFragment(): OrderAwaitingFragment
}
