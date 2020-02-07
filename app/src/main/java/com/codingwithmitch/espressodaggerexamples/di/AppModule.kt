package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.bumptech.glide.Glide
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.Constants.ApplicationMode
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/* Published bindings that will be mocked OR have test fakes */
/* Alternative for Test: 'TestAppModule' */
@Module
object AppModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideApplicationMode(): Constants.ApplicationMode {
        return Constants.ApplicationMode.NORMAL
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(
        application: Application,
        applicationMode: ApplicationMode
    ): GlideManager {
        return GlideRequestManager(
            Glide.with(application),
            applicationMode
        )
    }



}








