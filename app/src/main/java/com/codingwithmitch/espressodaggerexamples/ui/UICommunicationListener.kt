package com.codingwithmitch.espressodaggerexamples.ui

import com.codingwithmitch.espressodaggerexamples.models.Category

interface UICommunicationListener {

    fun showCategoriesMenu(categories: List<Category>)

    fun hideCategoriesMenu()
}