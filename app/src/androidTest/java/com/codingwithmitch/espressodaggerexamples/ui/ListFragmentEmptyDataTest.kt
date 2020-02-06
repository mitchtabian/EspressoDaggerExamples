package com.codingwithmitch.espressodaggerexamples.ui


import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.fragments.MockFragmentFactory
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.repository.MainRepositoryImpl
import com.codingwithmitch.espressodaggerexamples.ui.BlogPostListAdapter.*
import com.codingwithmitch.espressodaggerexamples.util.*
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentEmptyDataTest{

    private val CLASS_NAME = "ListFragmentTest"

    @Inject
    lateinit var viewModelFactory: MockMainViewModelFactory

    @Inject
    lateinit var jsonUtil: JsonUtil

//    @Inject
//    @Named("empty_blog_posts_data_filename")
//    lateinit var empty_list_blog_posts_filename: String
//
//    @Inject
//    @Named("blog_posts_data_filename")
//    lateinit var blog_posts_filename: String
//
//    @Inject
//    @Named("categories_data_filename")
//    lateinit var categories_filename: String

    lateinit var appComponent: TestAppComponent

//    lateinit var uiCommunicationListener: UICommunicationListener

//    lateinit var requestManager: GlideRequestManager

//    lateinit var fragmentFactory: MockFragmentFactory
//
//    lateinit var viewModelFactory: MockMainViewModelFactory

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()


    @Before
    fun prepareMocks(){

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        appComponent = DaggerTestAppComponent.builder()
            .application(app)
            .build()

        appComponent.inject(this)

//        requestManager = mockk<GlideRequestManager>()
//        every {
//            requestManager
//                .setImage(any(), any())
//        } just runs
//
//        uiCommunicationListener = mockk<UICommunicationListener>()
//        every {
//            uiCommunicationListener.showCategoriesMenu(allAny())
//        } just runs

    }

    private fun resetIdlingResource(){
        while(true){
            if(EspressoIdlingResource.clear()){
                break
            }
        }
    }


    @Test
    fun recyclerView_hasEmptyBlogList() {

//        resetIdlingResource()

//        val viewModelFactory = MockMainViewModelFactory(
//            MainRepositoryImpl(
//                FakeApiService(
//                    jsonUtil,
//                    empty_list_blog_posts_filename, // empty list
//                    categories_filename
//                )
//            )
//        )

        val uiCommunicationListener = mockk<UICommunicationListener>()
        every {
            uiCommunicationListener.showCategoriesMenu(allAny())
        } just runs

        val requestManager = mockk<GlideRequestManager>()
        every {
            requestManager
                .setImage(any(), any())
        } just runs

        val fragmentFactory = MockFragmentFactory(
            viewModelFactory,
            uiCommunicationListener,
            requestManager
        )

        // Begin
        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        scenario.onFragment { fragment ->

            fragment.viewModel.viewState.observe(fragment.viewLifecycleOwner, Observer { viewState ->
                printLogD(CLASS_NAME, "blogs: ${viewState.listFragmentView.blogs?.size}")
            })
        }

        onView(withId(R.id.no_data_textview))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }


    // TODO("Restoring list position (config change and process death?)")


    // MainActivity tests
    // TODO("is categories displayed?")
    // TODO("Changing category")

}

















