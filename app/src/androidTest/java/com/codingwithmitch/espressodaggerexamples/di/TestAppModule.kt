package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.bumptech.glide.Glide
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.Constants.ApplicationMode
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestAppModule
{
    @JvmStatic
    @Singleton
    @Provides
    fun provideApplicationMode(): Constants.ApplicationMode {
        return Constants.ApplicationMode.TESTING
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(
        application: Application,
        applicationMode: ApplicationMode
    ): GlideRequestManager {
        return GlideRequestManager(
            Glide.with(application),
            applicationMode
        )
    }



}








