package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import retrofit2.Response

interface MainRepository {

    suspend fun getBlogs(category: String): List<BlogPost>

    suspend fun getAllBlogs(): List<BlogPost>
}