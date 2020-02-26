package com.codingwithmitch.espressodaggerexamples.ui


import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.ui.BlogPostListAdapter.*
import com.codingwithmitch.espressodaggerexamples.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.CATEGORIES_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.EMPTY_LIST
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * ListFragment Isolation tests (FragmentScenario)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTests: BaseMainActivityTests() {

    private val CLASS_NAME = "ListFragmentTests"

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()



    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent)
            .inject(this)
    }
}



















