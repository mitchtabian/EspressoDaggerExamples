package com.codingwithmitch.espressodaggerexamples.repository

import androidx.lifecycle.LiveData
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.ApiResult
import com.codingwithmitch.espressodaggerexamples.util.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MainRepository : Repository{

//    suspend fun getBlogs(category: String): ApiResult<List<BlogPost>>

//    fun getAllBlogs(coroutineScope: CoroutineScope): LiveData<DataState<MainViewState>>

//    suspend fun getAllBlogs(): ApiResult<List<BlogPost>>

//    suspend fun getCategories(): ApiResult<List<Category>>

    fun getBlogs(category: String): Flow<DataState<MainViewState>>

    fun getAllBlogs(): Flow<DataState<MainViewState>>

    fun getCategories(): Flow<DataState<MainViewState>>
}