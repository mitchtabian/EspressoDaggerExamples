package com.codingwithmitch.espressodaggerexamples.ui


import android.app.Application
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestRepositoryModule
import com.codingwithmitch.espressodaggerexamples.fragments.FakeMainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.BlogPostListAdapter.*
import com.codingwithmitch.espressodaggerexamples.util.*
import com.codingwithmitch.espressodaggerexamples.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.CATEGORIES_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.EMPTY_LIST
import com.codingwithmitch.espressodaggerexamples.util.Constants.NETWORK_ERROR_TIMEOUT
import com.codingwithmitch.espressodaggerexamples.util.Constants.SERVER_ERROR_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.UNKNOWN_ERROR
import com.codingwithmitch.espressodaggerexamples.viewmodels.FakeMainViewModelFactory
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import javax.inject.Inject

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTests{

    private val CLASS_NAME = "ListFragmentTest"

    @Inject
    lateinit var viewModelFactory: FakeMainViewModelFactory

    @Inject
    lateinit var requestManager: FakeGlideRequestManager

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    private fun setupDependencies(apiService: ApiService, application: Application): FragmentFactory{

        val appComponent = DaggerTestAppComponent.builder()
            .repositoryModule(TestRepositoryModule(apiService))
            .application(application)
            .build()

        appComponent.inject(this)

        val uiCommunicationListener = mockk<UICommunicationListener>()
        every {
            uiCommunicationListener.showCategoriesMenu(allAny())
        } just runs
        return FakeMainFragmentFactory(
            viewModelFactory,
            uiCommunicationListener,
            requestManager
        )
    }

    @Test
    fun is_blogListEmpty() {

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        val apiService = FakeApiService(
            JsonUtil(app),
            EMPTY_LIST, // empty list
            CATEGORIES_DATA_FILENAME,
            0L
        )
        val fragmentFactory = setupDependencies(apiService, app)

        // Begin
        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        onView(withId(R.id.no_data_textview))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun is_recyclerViewItemsSet_validData() {

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        val apiService = FakeApiService(
            JsonUtil(app),
            BLOG_POSTS_DATA_FILENAME, // real list of blogs
            CATEGORIES_DATA_FILENAME,
            0L
        )
        val fragmentFactory = setupDependencies(apiService, app)

        // Begin
        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(
                RecyclerViewActions.scrollToPosition<BlogPostViewHolder>(5)
            )
        onView(withText("Mountains in Washington")).check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostViewHolder>(8)
        )
        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostViewHolder>(0)
        )
        onView(withText("Vancouver PNE 2019")).check(matches(isDisplayed()))

        onView(withId(R.id.no_data_textview))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun is_recyclerViewPositionRestoredAfterFragmentRecreated() {

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        val apiService = FakeApiService(
            JsonUtil(app),
            BLOG_POSTS_DATA_FILENAME,
            CATEGORIES_DATA_FILENAME,
            0L
        )
        val fragmentFactory = setupDependencies(apiService, app)

        // Begin
        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostViewHolder>(8)
        )
        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

        scenario.recreate()

        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

    }


}



















