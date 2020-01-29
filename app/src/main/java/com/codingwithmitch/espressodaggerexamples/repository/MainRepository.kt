package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.DataState
import kotlinx.coroutines.flow.Flow

interface MainRepository : Repository{

    fun getBlogs(category: String): Flow<DataState<MainViewState>>

    fun getAllBlogs(): Flow<DataState<MainViewState>>

    fun getCategories(): Flow<DataState<MainViewState>>
}