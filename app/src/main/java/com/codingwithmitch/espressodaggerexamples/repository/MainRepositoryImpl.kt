package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService
) : MainRepository{

    override suspend fun getBlogs(category: String): List<BlogPost> {
        val response = apiService.getBlogPosts()

        // CONTINUE HERE... Leverage a GenericApiResponse

        val result: ArrayList<BlogPost> = ArrayList()
        for(blog in apiService.getBlogPosts()){
            if(blog.category.equals(category)){
                result.add(blog)
            }
        }
        return result
    }

    override suspend fun getAllBlogs(): List<BlogPost> {
        return apiService.getBlogPosts()
    }

}













