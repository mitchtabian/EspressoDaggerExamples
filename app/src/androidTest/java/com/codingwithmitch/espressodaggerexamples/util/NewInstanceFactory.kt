package com.codingwithmitch.espressodaggerexamples.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class NewInstanceFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            modelClass.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}