package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.ApiResult

interface MainRepository : Repository{

    suspend fun getBlogs(category: String): ApiResult<List<BlogPost>>

    suspend fun getAllBlogs(): ApiResult<List<BlogPost>>

    suspend fun getCategories(): ApiResult<List<Category>>
}