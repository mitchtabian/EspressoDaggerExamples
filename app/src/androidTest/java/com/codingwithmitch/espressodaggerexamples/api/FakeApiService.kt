package com.codingwithmitch.espressodaggerexamples.api

import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FakeApiService
@Inject
constructor(
    private val jsonUtil: JsonUtil,
    private @Named("blog_posts_data_filename") val blogPostsJsonFileName: String,
    private @Named("categories_data_filename") val categoriesJsonFileName: String
): ApiService {

    override suspend fun getBlogPosts(category: String): List<BlogPost> {
        val rawJson = jsonUtil.readJSONFromAsset(blogPostsJsonFileName)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        val filteredBlogs = blogs.filter { blogPost -> blogPost.category.equals(category) }
        return filteredBlogs
    }

    override suspend fun getAllBlogPosts(): List<BlogPost> {
        val rawJson = jsonUtil.readJSONFromAsset(blogPostsJsonFileName)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        return blogs
    }

    override suspend fun getCategories(): List<Category> {
        val rawJson = jsonUtil.readJSONFromAsset(categoriesJsonFileName)
        val categories = Gson().fromJson<List<Category>>(
            rawJson,
            object : TypeToken<List<Category>>() {}.type
        )
        return categories
    }

}