package com.codingwithmitch.espressodaggerexamples.di

import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.espressodaggerexamples.fragments.MainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import dagger.Module
import dagger.Provides
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


