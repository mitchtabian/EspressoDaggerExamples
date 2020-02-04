package com.codingwithmitch.espressodaggerexamples.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.fragments.MockFragmentFactory
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


import org.junit.runner.RunWith
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest{

    private val CLASS_NAME = "ListFragmentTest"

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    lateinit var appComponent: TestAppComponent

    @get: Rule
    val espressoIdlingResoureRule = EspressoIdlingResourceRule()

//    @get:Rule
//    val activityRule = ActivityTestRule(MainActivity::class.java)

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

        val uiCommunicationListener = mockk<UICommunicationListener>()
        every {
            uiCommunicationListener.showCategoriesMenu(allAny())
        } just runs
        val fragmentFactory = MockFragmentFactory(
            viewModelFactory,
            uiCommunicationListener
        )
        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        scenario.onFragment { fragment ->

            fragment.viewModel.viewState.observe(fragment.viewLifecycleOwner, Observer { viewState ->
                printLogD(CLASS_NAME, "viewstate: ${viewState}")
            })

        }

    }




}

















