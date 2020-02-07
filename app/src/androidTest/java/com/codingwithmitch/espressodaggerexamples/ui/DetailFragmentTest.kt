package com.codingwithmitch.espressodaggerexamples.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
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
import com.codingwithmitch.espressodaggerexamples.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.Constants.CATEGORIES_DATA_FILENAME
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import com.codingwithmitch.espressodaggerexamples.viewmodels.FakeMainViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.*
import kotlinx.android.synthetic.main.fragment_detail.*
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
    lateinit var viewModelFactory: FakeMainViewModelFactory

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

    val requestManager = mockk<GlideRequestManager>()

    lateinit var appComponent: TestAppComponent

    @Before
    fun init(){
        val validDataApiService = FakeApiService(
            JsonUtil(app),
            BLOG_POSTS_DATA_FILENAME,
            CATEGORIES_DATA_FILENAME,
            0L
        )
        appComponent = DaggerTestAppComponent.builder()
            .repositoryModule(TestRepositoryModule(validDataApiService))
            .application(app)
            .build()

        appComponent.inject(this)

        every { requestManager.setImage(any(), any()) } just runs
    }

    @Test
    fun is_selectedBlogPostDetailsSet() {

        val uiCommunicationListener = mockk<UICommunicationListener>()
        every { uiCommunicationListener.showStatusBar() } just runs
        every { uiCommunicationListener.expandAppBar() } just runs
        every { uiCommunicationListener.hideCategoriesMenu() } just runs

        val fragmentFactory = FakeMainFragmentFactory(
            viewModelFactory,
            uiCommunicationListener,
            requestManager
        )

        val scenario = launchFragmentInContainer<DetailFragment>(
            factory = fragmentFactory
        )

        val rawJson = JsonUtil(app).readJSONFromAsset(BLOG_POSTS_DATA_FILENAME)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        val selectedBlogPost = blogs.get(0)
        scenario.onFragment { fragment ->
            fragment.viewModel.setSelectedBlogPost(selectedBlogPost)

            verify { requestManager.setImage(selectedBlogPost.image, fragment.blog_image) }
        }

        onView(ViewMatchers.withId(R.id.blog_title))
            .check(matches(withText(selectedBlogPost.title)))

        onView(ViewMatchers.withId(R.id.blog_category))
            .check(matches(withText(selectedBlogPost.category)))

        onView(ViewMatchers.withId(R.id.blog_body))
            .check(matches(withText(selectedBlogPost.body)))
    }
}











