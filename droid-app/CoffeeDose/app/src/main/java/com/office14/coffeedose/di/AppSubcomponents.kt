package com.office14.coffeedose.di

import dagger.Module


@Module(
    subcomponents = [
        CatalogComponent::class,
        OrdersComponent::class,
        OrderAwaitingComponent::class
    ]
)
class AppSubcomponents
