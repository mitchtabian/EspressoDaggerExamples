package com.codingwithmitch.espressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Integration tests
 * Ex:
 * 1) Launch app and check ListFragment
 * 2) Navigate and check various fragments are correct
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityIntegrationTests {

    private val CLASS_NAME = "ListFragmentErrorTests"

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    private fun configureApiService(
        blogsDataSource: String? = null,
        categoriesDataSource: String? = null,
        networkDelay: Long? = null,
        application: TestBaseApplication
    ) {
        val apiService = (application.appComponent as TestAppComponent).apiService
        blogsDataSource?.let { apiService.blogPostsJsonFileName = it }
        categoriesDataSource?.let { apiService.categoriesJsonFileName = it }
        networkDelay?.let { apiService.networkDelay = it }
    }

    private fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

    @Test
    fun isListFragmentVisible_onAppLaunch() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        injectTest(app)

        configureApiService(
            blogsDataSource = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L, // not needed since default is 0L
            application = app
        )

        val scenario = launchActivity<MainActivity>()

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(5)
        )
        onView(withText("Mountains in Washington")).check(matches(isDisplayed()))

    }

}
















