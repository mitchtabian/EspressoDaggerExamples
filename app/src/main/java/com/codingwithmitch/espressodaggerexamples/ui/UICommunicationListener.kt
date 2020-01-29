package com.codingwithmitch.espressodaggerexamples.ui

import com.codingwithmitch.espressodaggerexamples.models.Category

interface UICommunicationListener {

    fun showCategoriesMenu(categories: ArrayList<Category>)

    fun hideCategoriesMenu()

    fun displayMainProgressBar(isLoading: Boolean)

    fun hideToolbar()

    fun showToolbar()

    fun hideStatusBar()

    fun showStatusBar()

    fun expandAppBar()

    fun displayToastMessage(message: String, length: Int)

    fun displaySnackbar(message: String, length: Int)
}