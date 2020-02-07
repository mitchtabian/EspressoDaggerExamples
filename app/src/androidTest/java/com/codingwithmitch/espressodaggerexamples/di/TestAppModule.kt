package com.codingwithmitch.espressodaggerexamples.di

import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.util.FakeGlideRequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestAppModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(): GlideManager {
        return FakeGlideRequestManager()
    }
}