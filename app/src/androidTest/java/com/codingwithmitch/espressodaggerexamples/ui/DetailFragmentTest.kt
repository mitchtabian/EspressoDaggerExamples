package com.codingwithmitch.espressodaggerexamples.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.fragments.MockFragmentFactory
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class DetailFragmentTest {

    private val CLASS_NAME = "DetailFragmentTest"

    @Inject
    lateinit var viewModelFactory: MockMainViewModelFactory

    @Inject
    lateinit var requestManager: GlideRequestManager

    lateinit var appComponent: TestAppComponent

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Before
    fun prepareMocks(){

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        appComponent = DaggerTestAppComponent.builder()
            .application(app)
            .build()

        appComponent.inject(this)
    }

    @Test
    fun isDetailFragmentVisible() {

        val uiCommunicationListener = mockk<UICommunicationListener>()
        every { uiCommunicationListener.showStatusBar() } just runs
        every { uiCommunicationListener.expandAppBar() } just runs
        every { uiCommunicationListener.hideCategoriesMenu() } just runs

        val fragmentFactory = MockFragmentFactory(
            viewModelFactory,
            uiCommunicationListener,
            requestManager
        )

        val scenario = launchFragmentInContainer<DetailFragment>(
            factory = fragmentFactory
        )

        Espresso.onView(ViewMatchers.withId(R.id.blog_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}











