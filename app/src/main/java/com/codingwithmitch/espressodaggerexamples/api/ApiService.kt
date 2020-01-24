package com.codingwithmitch.espressodaggerexamples.api

import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // will handle filtering client side (categories)
    @GET("blogs")
    suspend fun getBlogPosts(): Response<List<BlogPost>>

    companion object{
        const val BASE_URL = "https://open-api.xyz/placeholder"
    }
}