package com.codingwithmitch.espressodaggerexamples.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.daggermultifeature.feature1.di.keys.MainFragmentKey
import com.codingwithmitch.espressodaggerexamples.fragments.MainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.DetailFragment
import com.codingwithmitch.espressodaggerexamples.ui.FinalFragment
import com.codingwithmitch.espressodaggerexamples.ui.ListFragment
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object FragmentModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: MainViewModelFactory,
        glideManager: GlideManager
    ): FragmentFactory{
        return MainFragmentFactory(viewModelFactory, glideManager)
    }

}















