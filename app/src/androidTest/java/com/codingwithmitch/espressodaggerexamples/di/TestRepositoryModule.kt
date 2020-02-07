package com.codingwithmitch.espressodaggerexamples.di

import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestRepositoryModule
constructor(
    private val apiService: ApiService
){

    // ApiService is passed as constructor arg so we can pass different
    // data sets for each @Test.
    // Ex: empty data, real data, data that causes errors, whatever.

    @Singleton
    @Provides
    fun provideMainRepository(): MainRepository {
        return MainRepositoryImpl(apiService)
    }

}















