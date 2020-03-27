package com.office14.coffeedose.di

import androidx.lifecycle.ViewModel
import com.office14.coffeedose.ui.CoffeeDoseActivity
import com.office14.coffeedose.viewmodels.MenuInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MenuInfoViewModel::class)
    abstract fun bindMenuInfoViewModel(viewModel: MenuInfoViewModel): ViewModel

    /*@ContributesAndroidInjector
    internal abstract fun contributeCoffeeDoseActivity(): CoffeeDoseActivity*/

    @Module
    companion object {
        @Provides
        fun getMessage() = "its message"
    }


}