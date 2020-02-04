package com.codingwithmitch.espressodaggerexamples.ui

import android.app.Application
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.FakeAppModule
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.fragments.MainFragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test


import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.test.assertEquals

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest{

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    lateinit var appComponent: TestAppComponent

    @Before
    fun beforeTests(){

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        appComponent = DaggerTestAppComponent.builder()
            .application(app)
            .build()

        appComponent.inject(this)
    }

    @Test
    fun test() {

        val scenario = launchFragmentInContainer<ListFragment>(
            factory = mainFragmentFactory
        )
        scenario.onFragment { fragment ->
            assertEquals(FakeAppModule.SOME_STRING, fragment.someString)
        }
    }
}

















