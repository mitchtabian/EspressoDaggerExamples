package com.codingwithmitch.espressodaggerexamples.di

import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.espressodaggerexamples.fragments.FakeMainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.viewmodels.FakeMainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object TestFragmentModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: FakeMainViewModelFactory,
        glideManager: GlideManager
    ): FragmentFactory {
        return FakeMainFragmentFactory(viewModelFactory, glideManager)
    }
}