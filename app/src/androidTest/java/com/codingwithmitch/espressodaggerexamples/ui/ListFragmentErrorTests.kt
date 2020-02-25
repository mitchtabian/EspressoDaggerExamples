package com.codingwithmitch.espressodaggerexamples.ui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.FakeGlideRequestManager
import com.codingwithmitch.espressodaggerexamples.viewmodels.FakeMainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import javax.inject.Inject

/**
 * Separate class for the error testing because because the error dialogs are shown in MainActivity.
 * So need ActivityScenario, not FragmentScenario.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentErrorTests {

    private val CLASS_NAME = "ListFragmentErrorTests"

    @Inject
    lateinit var viewModelFactory: FakeMainViewModelFactory

    @Inject
    lateinit var requestManager: FakeGlideRequestManager

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun isListFragmentVisible_onAppLaunch() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val scenario = launchActivity<MainActivity>()

        onView(withId(R.id.main_parent)).check(matches(isDisplayed()))

//        onView(withId(R.id.app_bar))
//            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

//    @Test
//    fun is_dataErrorShown() {
//
//        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication
//
//        val apiService = FakeApiService(
//            JsonUtil(app),
//            Constants.SERVER_ERROR_FILENAME, // data that will cause UNKNOWN ERROR
//            Constants.CATEGORIES_DATA_FILENAME,
//            0L
//        )
//        val fragmentFactory = setupDependencies(apiService, app)
//
//        // Begin
//        val scenario = launchFragmentInContainer<ListFragment>(
//            factory = fragmentFactory
//        )
//
//        onView(withText(R.string.text_error))
//            .check(matches(isDisplayed()))
//
//        onView(withText(Constants.UNKNOWN_ERROR))
//            .check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun is_networkTimeoutDialogShown() {
//
//        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication
//
//        val apiService = FakeApiService(
//            JsonUtil(app),
//            Constants.BLOG_POSTS_DATA_FILENAME,
//            Constants.CATEGORIES_DATA_FILENAME,
//            4000L // 4000 > 3000 so it will timeout
//        )
//        val fragmentFactory = setupDependencies(apiService, app)
//
//        // Begin
//        val scenario = launchFragmentInContainer<ListFragment>(
//            factory = fragmentFactory
//        )
//
//        onView(withText(R.string.text_error))
//            .check(matches(isDisplayed()))
//
//        onView(withText(Constants.NETWORK_ERROR_TIMEOUT))
//            .check(matches(isDisplayed()))
//    }

}













