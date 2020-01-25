package com.codingwithmitch.espressodaggerexamples.ui

import com.codingwithmitch.espressodaggerexamples.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)

    fun onToolbarLoading(isLoading: Boolean)
}