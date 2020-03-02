package com.codingwithmitch.espressodaggerexamples.ui

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ListFragment integration tests (ActivityScenario).
 * Launch app and check ListFragment properties (menu, recyclerview).
 *
 */
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentIntegrationTests: BaseMainActivityTests() {

    private val CLASS_NAME = "MainActivityIntegrationTests"

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun isBlogListEmpty() {

        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = Constants.EMPTY_LIST, // empty list of data
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        onView(withId(R.id.no_data_textview))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }


    // if query for categories returns an empty list, the user should still see
    // the menu item "All"
    @Test
    fun isCategoryListEmpty() {

        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        val apiService = configureFakeApiService(
            blogsDataSource = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = Constants.EMPTY_LIST, // empty list of data
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        val scenario = launchActivity<MainActivity>().onActivity { mainActivity ->
            val toolbar: Toolbar = mainActivity.findViewById(R.id.tool_bar)

            // wait for jobs to complete to open the menu
            mainActivity.viewModel.viewState.observe(mainActivity, Observer { viewState ->
                if(viewState.activeJobCounter.size == 0){
                    toolbar.showOverflowMenu()
                }
            })
        }

        onView(withSubstring("earthporn"))
            .check(doesNotExist())

        onView(withSubstring("dogs"))
            .check(doesNotExist())

        onView(withSubstring("fun"))
            .check(doesNotExist())

        onView(withSubstring("All"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkListData_testScrolling() {

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

        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(5)
        )
        onView(withText("Mountains in Washington")).check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(8)
        )
        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(0)
        )
        onView(withText("Vancouver PNE 2019")).check(matches(isDisplayed()))

        onView(withId(R.id.no_data_textview))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun checkListData_onCategoryChange_toEarthporn(){
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

        val scenario = launchActivity<MainActivity>().onActivity { mainActivity ->
            val toolbar: Toolbar = mainActivity.findViewById(R.id.tool_bar)

            mainActivity.viewModel.viewState.observe(mainActivity, object: Observer<MainViewState>{
                override fun onChanged(viewState: MainViewState?) {
                    if(viewState?.activeJobCounter?.size == 0){
                        toolbar.showOverflowMenu()
                        mainActivity.viewModel.viewState.removeObserver(this)
                    }
                }
            })
        }

        // click "earthporn" category from menu
        val CATEGORY_NAME = "earthporn"
        onView(withText(CATEGORY_NAME)).perform(click())

        onView(withText("Mountains in Washington"))
            .check(matches(isDisplayed()))

        onView(withText("France Mountain Range"))
            .check(matches(isDisplayed()))

        onView(withText("Vancouver PNE 2019"))
            .check(doesNotExist())
    }


    @Test
    fun checkListData_onCategoryChange_toFun(){
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

        val scenario = launchActivity<MainActivity>().onActivity { mainActivity ->
            val toolbar: Toolbar = mainActivity.findViewById(R.id.tool_bar)

            mainActivity.viewModel.viewState.observe(mainActivity, object: Observer<MainViewState>{
                override fun onChanged(viewState: MainViewState?) {
                    if(viewState?.activeJobCounter?.size == 0){
                        toolbar.showOverflowMenu()
                        mainActivity.viewModel.viewState.removeObserver(this)
                    }
                }
            })
        }

        // click "fun" category from menu
        val CATEGORY_NAME = "fun"
        onView(withText(CATEGORY_NAME)).perform(click())

        onView(withText("My Brother Blake"))
            .check(matches(isDisplayed()))

        onView(withText("Vancouver PNE 2019"))
            .check(matches(isDisplayed()))

        onView(withText("France Mountain Range"))
            .check(doesNotExist())
    }

    @Test
    fun isInstanceStateSavedAndRestored_OnDestroyActivity() {

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

        val scenario = launchActivity<MainActivity>()

        onView(withId(R.id.recycler_view))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(8)
        )

        onView(withText("Blake Posing for his Website"))
            .check(matches(isDisplayed()))

        scenario.recreate()

        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

    }


    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}









