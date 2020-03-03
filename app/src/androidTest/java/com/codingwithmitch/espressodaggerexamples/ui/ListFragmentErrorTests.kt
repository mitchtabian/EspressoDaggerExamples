package com.codingwithmitch.espressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.CATEGORIES_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.SERVER_ERROR_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResource
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentErrorTests: BaseMainActivityTests() {


    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()


    @Test
    fun isErrorDialogShown_UnknownError() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = SERVER_ERROR_FILENAME, // force "Unknown error"
            categoriesDataSource = CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))

        onView(withSubstring(Constants.UNKNOWN_ERROR)).check(matches(isDisplayed()))
    }

    @Test
    fun doesNetworkTimeout_networkTimeoutError() {

        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = CATEGORIES_DATA_FILENAME,
            networkDelay = 4000L, // force timeout (4000 > 3000)
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error))
            .check(matches(isDisplayed()))

        onView(withSubstring(Constants.NETWORK_ERROR_TIMEOUT))
            .check(matches(isDisplayed()))

    }


    @Test
    fun isErrorDialogShown_CannotRetrieveCategories() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = SERVER_ERROR_FILENAME, // force error
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))

        onView(withSubstring(MainStateEvent.GetCategories().errorInfo()))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isErrorDialogShown_CannotRetrieveBlogPosts() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = SERVER_ERROR_FILENAME, // force error
            categoriesDataSource = CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))

        onView(withSubstring(MainStateEvent.GetAllBlogs().errorInfo()))
            .check(matches(isDisplayed()))
    }


    override fun injectTest(application: TestBaseApplication){
        (application.appComponent as TestAppComponent)
            .inject(this)
    }


}
















