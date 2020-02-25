package com.codingwithmitch.espressodaggerexamples.di

import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestRepositoryModule
{

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return FakeApiService()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainRepository(apiService: ApiService): MainRepository {
        return MainRepositoryImpl(apiService)
    }

}















