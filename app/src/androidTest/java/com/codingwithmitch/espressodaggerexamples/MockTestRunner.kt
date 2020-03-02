package com.codingwithmitch.espressodaggerexamples

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MockTestRunner: AndroidJUnitRunner(){

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ) :  Application {
        return super.newApplication(cl, TestBaseApplication::class.java.name, context)
    }
}