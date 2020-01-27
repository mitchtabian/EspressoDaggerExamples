package com.codingwithmitch.espressodaggerexamples.ui.state

sealed class MainStateEvent {

    class GetAllBlogs: MainStateEvent()

    class GetCategories: MainStateEvent()

    data class SearchBlogsByCategory(
        val category: String
    ): MainStateEvent()


}