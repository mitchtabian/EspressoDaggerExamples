package com.codingwithmitch.espressodaggerexamples.api

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeApiService
@Inject
constructor(): ApiService {

    lateinit var jsonUtil: JsonUtil
    var blogPostsJsonFileName: String = Constants.BLOG_POSTS_DATA_FILENAME
    var categoriesJsonFileName: String = Constants.CATEGORIES_DATA_FILENAME
    var networkDelay: Long = 0L

    fun initJsonUtil(application: Application){
        jsonUtil = JsonUtil(application)
    }

    private fun throwExceptionIfJsonUtilNotInitialized(){
        if(!::jsonUtil.isInitialized){
            throw UninitializedPropertyAccessException("Must initialize JsonUtil before running test.")
        }
    }

    @Throws(UninitializedPropertyAccessException::class)
    override suspend fun getBlogPosts(category: String): List<BlogPost> {
        throwExceptionIfJsonUtilNotInitialized()
        val rawJson = jsonUtil.readJSONFromAsset(blogPostsJsonFileName)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        val filteredBlogs = blogs.filter { blogPost -> blogPost.category.equals(category) }
        delay(networkDelay)
        return filteredBlogs
    }

    @Throws(UninitializedPropertyAccessException::class)
    override suspend fun getAllBlogPosts(): List<BlogPost> {
        throwExceptionIfJsonUtilNotInitialized()
        val rawJson = jsonUtil.readJSONFromAsset(blogPostsJsonFileName)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        delay(networkDelay)
        return blogs
    }

    @Throws(UninitializedPropertyAccessException::class)
    override suspend fun getCategories(): List<Category> {
        throwExceptionIfJsonUtilNotInitialized()
        val rawJson = jsonUtil.readJSONFromAsset(categoriesJsonFileName)
        val categories = Gson().fromJson<List<Category>>(
            rawJson,
            object : TypeToken<List<Category>>() {}.type
        )
        delay(networkDelay)
        return categories
    }

}