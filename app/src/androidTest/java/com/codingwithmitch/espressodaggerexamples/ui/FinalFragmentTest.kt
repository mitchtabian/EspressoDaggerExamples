package com.codingwithmitch.espressodaggerexamples.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestRepositoryModule
import com.codingwithmitch.espressodaggerexamples.fragments.FakeMainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.setSelectedBlogPost
import com.codingwithmitch.espressodaggerexamples.util.Constants
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import com.codingwithmitch.espressodaggerexamples.viewmodels.FakeMainViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_final.*
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
class FinalFragmentTest {

    private val CLASS_NAME = "FinalFragmentTest"

    @Inject
    lateinit var viewModelFactory: FakeMainViewModelFactory

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

    val requestManager = mockk<GlideRequestManager>()

    val uiCommunicationListener = mockk<UICommunicationListener>()

    lateinit var appComponent: TestAppComponent

    lateinit var fragmentFactory: FakeMainFragmentFactory

    @Before
    fun init(){
        every { requestManager.setImage(any(), any()) } just runs
        every { uiCommunicationListener.hideStatusBar() } just runs

        val apiService = FakeApiService(
            JsonUtil(app),
            Constants.BLOG_POSTS_DATA_FILENAME,
            Constants.CATEGORIES_DATA_FILENAME,
            0L
        )
        appComponent = DaggerTestAppComponent.builder()
            .repositoryModule(TestRepositoryModule(apiService))
            .application(app)
            .build()

        appComponent.inject(this)

        fragmentFactory = FakeMainFragmentFactory(
            viewModelFactory,
            uiCommunicationListener,
            requestManager
        )
    }

    @Test
    fun is_scalingImageView() {

        val scenario = launchFragmentInContainer<FinalFragment>(
            factory = fragmentFactory
        )

        val rawJson = JsonUtil(app).readJSONFromAsset(Constants.BLOG_POSTS_DATA_FILENAME)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        val selectedBlogPost = blogs.get(0)
        scenario.onFragment { fragment ->
            fragment.viewModel.setSelectedBlogPost(selectedBlogPost)

            verify { requestManager.setImage(selectedBlogPost.image, fragment.scaling_image_view) }
        }

        onView(withId(R.id.scaling_image_view)).check(matches(isDisplayed()))
    }
}

















