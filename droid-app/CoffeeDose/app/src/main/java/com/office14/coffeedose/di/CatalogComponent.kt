package com.office14.coffeedose.di

import dagger.Module
import dagger.Subcomponent

@Module(
    subcomponents = [
        DrinksComponent::class,
        SelectDoseComponent::class
    ]
)
class CatalogSubComponents

@Subcomponent(
    modules = [
        CatalogSubComponents::class
    ]
)
interface CatalogComponent  {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CatalogComponent
    }
}