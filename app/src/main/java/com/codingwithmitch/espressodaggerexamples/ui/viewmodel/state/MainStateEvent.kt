package com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state

sealed class MainStateEvent {

    class GetAllBlogs: MainStateEvent(){

        override fun toString(): String {
            return "GetAllBlogs"
        }
    }

    class GetCategories: MainStateEvent(){

        override fun toString(): String {
            return "GetCategories"
        }
    }

    data class SearchBlogsByCategory(
        val category: String
    ): MainStateEvent(){

        override fun toString(): String {
            return "SearchBlogsByCategory"
        }
    }


}