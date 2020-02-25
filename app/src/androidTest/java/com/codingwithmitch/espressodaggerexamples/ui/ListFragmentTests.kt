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
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.fragments.FakeMainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.BlogPostListAdapter.*
import com.codingwithmitch.espressodaggerexamples.util.*
import com.codingwithmitch.espressodaggerexamples.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.CATEGORIES_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.EMPTY_LIST
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

    private fun buildFragmentFactory(): FragmentFactory{

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

    private fun configureApiService(
        blogsDataSource: String? = null,
        categoriesDataSource: String? = null,
        networkDelay: Long? = null,
        application: TestBaseApplication
    ){
        val apiService = (application.appComponent as TestAppComponent).apiService
        apiService.initJsonUtil(application)
        blogsDataSource?.let { apiService.blogPostsJsonFileName = it }
        categoriesDataSource?.let { apiService.categoriesJsonFileName = it }
        networkDelay?.let { apiService.networkDelay = it }
    }

    private fun injectTest(application: TestBaseApplication){
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

    @Test
    fun is_blogListEmpty() {

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        injectTest(app)

        configureApiService(
            blogsDataSource = EMPTY_LIST,
            categoriesDataSource = CATEGORIES_DATA_FILENAME,
            networkDelay = 0L, // not needed since default is 0L
            application = app
        )
        
        val fragmentFactory = buildFragmentFactory()

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

        injectTest(app)

        configureApiService(
            blogsDataSource = BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = CATEGORIES_DATA_FILENAME,
            networkDelay = 0L, // not needed since default is 0L
            application = app
        )
        val fragmentFactory = buildFragmentFactory()

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

        injectTest(app)

        configureApiService(
            blogsDataSource = BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = CATEGORIES_DATA_FILENAME,
            networkDelay = 0L, // not needed since default is 0L
            application = app
        )

        val fragmentFactory = buildFragmentFactory()

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



















