package com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state

import com.codingwithmitch.espressodaggerexamples.util.StateEvent

sealed class MainStateEvent: StateEvent {

    class GetAllBlogs: MainStateEvent(){

        override fun errorInfo(): String{
            return "Unable to retrieve all blog posts."
        }

        override fun toString(): String {
            return "GetAllBlogs"
        }
    }

    class GetCategories: MainStateEvent(){

        override fun errorInfo(): String{
            return "Unable to retrieve categories."
        }

        override fun toString(): String {
            return "GetCategories"
        }
    }

    data class SearchBlogsByCategory(
        val category: String
    ): MainStateEvent(){

        override fun errorInfo(): String{
            return "Unable to retrieve all blog posts from the category '$category.'"
        }

        override fun toString(): String {
            return "SearchBlogsByCategory"
        }
    }


}