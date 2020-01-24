package com.codingwithmitch.espressodaggerexamples.di

import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.api.ApiService.Companion.BASE_URL
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.repository.MainRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {


    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(apiService: ApiService): MainRepository {
        return MainRepositoryImpl(apiService)
    }
}










