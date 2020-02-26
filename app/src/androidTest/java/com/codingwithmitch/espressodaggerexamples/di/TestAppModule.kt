package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.util.FakeGlideRequestManager
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
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

    @JvmStatic
    @Singleton
    @Provides
    fun provideJsonUtil(application: Application): JsonUtil {
        return JsonUtil(application)
    }
}