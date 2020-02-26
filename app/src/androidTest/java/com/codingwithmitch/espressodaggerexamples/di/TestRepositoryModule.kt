package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.repository.MainRepositoryImpl
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestRepositoryModule
{

    @JvmStatic
    @Singleton
    @Provides
    fun provideJsonUtil(application: Application): JsonUtil {
        return JsonUtil(application)
    }

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideApiService(jsonUtil: JsonUtil): ApiService {
//        return FakeApiService(jsonUtil)
//    }
//
//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideMainRepository(apiService: ApiService): MainRepository {
//        return MainRepositoryImpl(apiService)
//    }

}















