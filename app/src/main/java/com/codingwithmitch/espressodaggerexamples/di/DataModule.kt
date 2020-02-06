package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.bumptech.glide.Glide
import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.repository.MainRepositoryImpl
import com.codingwithmitch.espressodaggerexamples.util.Constants.ApplicationMode
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object DataModule{


    @JvmStatic
    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainRepository(apiService: ApiService): MainRepository {
        return MainRepositoryImpl(apiService)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideApplicationMode(): ApplicationMode{
        return ApplicationMode.NORMAL
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








