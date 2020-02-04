package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.CATEGORIES_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object FakeDataModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideJsonUtil(application: Application): JsonUtil {
        return JsonUtil(application)
    }


    @JvmStatic
    @Singleton
    @Provides
    @Named("blog_posts_data_filename")
    fun provideBlogPostsDataFileName(): String{
        return BLOG_POSTS_DATA_FILENAME
    }

    @JvmStatic
    @Singleton
    @Provides
    @Named("categories_data_filename")
    fun provideCategoriesDataFileName(): String{
        return CATEGORIES_DATA_FILENAME
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFakeApiService(
        jsonUtil: JsonUtil,
        @Named("blog_posts_data_filename") blogPostsDataFileName: String,
        @Named("categories_data_filename") categoriesDataFileName: String
    ): ApiService {
        return FakeApiService(
            jsonUtil,
            blogPostsDataFileName,
            categoriesDataFileName
        )
    }

}








