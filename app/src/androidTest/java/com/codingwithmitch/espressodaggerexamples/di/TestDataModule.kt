package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.bumptech.glide.Glide
import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.repository.MainRepositoryImpl
import com.codingwithmitch.espressodaggerexamples.util.Constants.ApplicationMode
import com.codingwithmitch.espressodaggerexamples.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.CATEGORIES_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.EMPTY_BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object TestDataModule{

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
    @Named("empty_blog_posts_data_filename")
    fun provideEmptyBlogPostsDataFileName(): String{
        return EMPTY_BLOG_POSTS_DATA_FILENAME
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

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainRepository_BlogListData(
        fakeApiService: FakeApiService
    ): MainRepository {
        return MainRepositoryImpl(fakeApiService)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideApplicationMode(): ApplicationMode {
        return ApplicationMode.TESTING
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








