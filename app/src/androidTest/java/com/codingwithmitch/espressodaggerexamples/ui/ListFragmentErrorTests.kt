package com.codingwithmitch.espressodaggerexamples.ui

import androidx.core.graphics.scaleMatrix
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.repository.FakeMainRepositoryImpl
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
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
 * Separate class for the error testing because because the error dialogs are shown in MainActivity.
 * So need ActivityScenario, not FragmentScenario.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentErrorTests {

    private val CLASS_NAME = "ListFragmentErrorTests"

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    private fun configureFakeApiService(
        blogsDataSource: String? = null,
        categoriesDataSource: String? = null,
        networkDelay: Long? = null,
        application: TestBaseApplication
    ): FakeApiService {
        val apiService = (application.appComponent as TestAppComponent).apiService
        blogsDataSource?.let { apiService.blogPostsJsonFileName = it }
        categoriesDataSource?.let { apiService.categoriesJsonFileName = it }
        networkDelay?.let { apiService.networkDelay = it }
        return apiService
    }

    private fun configureFakeRepository(
        apiService: FakeApiService,
        application: TestBaseApplication
    ): FakeMainRepositoryImpl {
        val mainRepository = (application.appComponent as TestAppComponent).mainRepository
        mainRepository.apiService = apiService
        return mainRepository
    }

    private fun injectTest(application: TestBaseApplication){
        (application.appComponent as TestAppComponent)
            .inject(this)
    }

    @Test
    fun isErrorDialogShown_UnknownError() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication

        injectTest(app)

        val apiService = configureFakeApiService(
            blogsDataSource = Constants.SERVER_ERROR_FILENAME,
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L, // not needed since default is 0L
            application = app
        )

        val repository = configureFakeRepository(apiService, app)

        val scenario = launchActivity<MainActivity>()

        onView(withText(R.string.text_error)).check(matches(isDisplayed()))

        onView(withSubstring(Constants.UNKNOWN_ERROR)).check(matches(isDisplayed()))
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













