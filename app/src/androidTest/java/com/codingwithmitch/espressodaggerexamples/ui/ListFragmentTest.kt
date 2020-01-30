package com.codingwithmitch.espressodaggerexamples.ui

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test


import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest{


    @Test
    fun test() {
        val app = InstrumentationRegistry.getInstrumentation().context.applicationContext

        val testAppComponent: TestAppComponent
    }
}

















