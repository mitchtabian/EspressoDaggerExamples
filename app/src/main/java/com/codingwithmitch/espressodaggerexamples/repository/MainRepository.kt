package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.util.StateEvent
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.DataState
import kotlinx.coroutines.flow.Flow

interface MainRepository{

    fun getBlogs(stateEvent: StateEvent, category: String): Flow<DataState<MainViewState>>

    fun getAllBlogs(stateEvent: StateEvent): Flow<DataState<MainViewState>>

    fun getCategories(stateEvent: StateEvent): Flow<DataState<MainViewState>>
}