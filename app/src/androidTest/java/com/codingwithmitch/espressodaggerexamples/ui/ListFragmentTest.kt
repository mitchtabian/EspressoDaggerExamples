package com.codingwithmitch.espressodaggerexamples.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.fragments.MainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


import org.junit.runner.RunWith
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest{

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    lateinit var appComponent: TestAppComponent

    @get: Rule
    val espressoIdlingResoureRule = EspressoIdlingResourceRule()


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

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.main_progress_bar)).check(matches(isDisplayed()))

        scenario.onFragment { fragment ->

            fragment.viewModel.viewState.observe(fragment.viewLifecycleOwner, Observer { viewState ->
                println("BLOGS: ${viewState.listFragmentView.blogs}")
            })
//            fragment.viewModel.setStateEvent(MainStateEvent.GetAllBlogs())
        }

        onView(withId(R.id.main_progress_bar)).check(matches(not(isDisplayed())))
    }
}

















