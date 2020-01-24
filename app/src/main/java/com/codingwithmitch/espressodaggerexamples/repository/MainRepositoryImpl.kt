package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.util.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class   MainRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService
) : MainRepository{

    override suspend fun getBlogs(category: String): ApiResult<List<BlogPost>> {
        return safeApiCall(IO){ apiService.getBlogPosts() }
    }

    override suspend fun getAllBlogs(): ApiResult<List<BlogPost>> {
        return safeApiCall(IO){apiService.getBlogPosts()}
//        return apiService.getBlogPosts()
    }

}













