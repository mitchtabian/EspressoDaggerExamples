package com.codingwithmitch.espressodaggerexamples.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.ui.BlogPostListAdapter.*
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Test the overall navigation with ActivityScenario
 * ListFragment -> DetailFragment -> FinalFragment
 */
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class MainNavigationTests : BaseMainActivityTests(){

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Test
    fun basicNavigationTest(){

        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        val rawJson = jsonUtil.readJSONFromAsset(Constants.BLOG_POSTS_DATA_FILENAME)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        val SELECTED_LIST_INDEX = 8 // chose 8 so the app has to scroll
        val selectedBlogPost = blogs.get(SELECTED_LIST_INDEX)

        val scenario = launchActivity<MainActivity>()

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<BlogPostViewHolder>(SELECTED_LIST_INDEX)
        )

        // Nav DetailFragment
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BlogPostViewHolder>(SELECTED_LIST_INDEX, click())
        )

        onView(withId(R.id.blog_title)).check(matches(withText(selectedBlogPost.title)))

        onView(withId(R.id.blog_body)).check(matches(withText(selectedBlogPost.body)))

        onView(withId(R.id.blog_category)).check(matches(withText(selectedBlogPost.category)))

        // Nav FinalFragment
        onView(withId(R.id.blog_image)).perform(click())

        onView(withId(R.id.scaling_image_view)).check(matches(isDisplayed()))

        // Back to DetailFragment
        pressBack()

        onView(withId(R.id.blog_title)).check(matches(withText(selectedBlogPost.title)))

        // Back to ListFragment
        pressBack()

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

}


















