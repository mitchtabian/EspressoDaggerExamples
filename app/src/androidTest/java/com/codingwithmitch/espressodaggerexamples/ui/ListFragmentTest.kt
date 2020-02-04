package com.codingwithmitch.espressodaggerexamples.ui


import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.internal.util.Checks
import androidx.test.platform.app.InstrumentationRegistry
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.fragments.MockFragmentFactory
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResourceRule
import com.codingwithmitch.espressodaggerexamples.util.JsonUtil
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import io.mockk.*
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest{

    private val CLASS_NAME = "ListFragmentTest"

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Inject
    @Named("blog_posts_data_filename")
    lateinit var blog_posts_filename: String

    lateinit var appComponent: TestAppComponent

    @get: Rule
    val espressoIdlingResoureRule = EspressoIdlingResourceRule()

//    @get:Rule
//    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun beforeTests(){

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestBaseApplication

        appComponent = DaggerTestAppComponent.builder()
            .application(app)
            .build()

        appComponent.inject(this)
    }

    @Test
    fun test() {

        val uiCommunicationListener = mockk<UICommunicationListener>()
        every {
            uiCommunicationListener.showCategoriesMenu(allAny())
        } just runs
        val fragmentFactory = MockFragmentFactory(
            viewModelFactory,
            uiCommunicationListener
        )
        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

//        scenario.onFragment { fragment ->

//            fragment.viewModel.viewState.observe(fragment.viewLifecycleOwner, Observer { viewState ->
//                printLogD(CLASS_NAME, "viewstate: ${viewState.listFragmentView.blogs}")

//                val blogPosts = jsonUtil.readJSONFromAsset(blog_posts_filename)
//                val blogPost = BlogPost(24, "Fdngfdg", "Gdfgfd ", "fdggdfg", "dfgdfgreg")
//                val blogPost = blogPosts!!.get(0)
//                assertThat(viewState.listFragmentView.blogs, hasItem(blogPost))
//            })

//        }


//        val blogPosts = jsonUtil.readJSONFromAsset(blog_posts_filename)
//
//        val blogPost = BlogPost(24, "Fdngfdg", "Gdfgfd ", "fdggdfg", "dfgdfgreg")

//        onView(withId(R.id.recycler_view))
//            .perform(RecyclerViewActions.scrollToHolder<BlogPostListAdapter.BlogPostViewHolder>(
//                withBlogPostTitleInViewHolder("Blake Posing for his Website")
//            ))
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<BlogPostListAdapter.BlogPostViewHolder>(8))
        onView(withText("Blake Posing for his Website")).check(matches(isDisplayed()))

    }


    fun withBlogPostTitleInViewHolder(blogPostTitle: String): TypeSafeMatcher<BlogPostListAdapter.BlogPostViewHolder> {
        Checks.checkNotNull(blogPostTitle)

        return object: TypeSafeMatcher<BlogPostListAdapter.BlogPostViewHolder>(){

            override fun describeTo(description: Description?) {
                description?.appendText("with blog post title: $blogPostTitle")
            }

            override fun matchesSafely(item: BlogPostListAdapter.BlogPostViewHolder?): Boolean {
                printLogD(CLASS_NAME, "matchesSafely: ${(blogPostTitle == item?.itemView?.blog_title?.text.toString())}")
                return (blogPostTitle == item?.itemView?.blog_title?.text.toString())
            }

        }
    }

}

















