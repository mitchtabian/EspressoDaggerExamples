package com.codingwithmitch.espressodaggerexamples.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.fragments.MainNavHostFragment
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.MainViewModel
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResource
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class DetailFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager
) : Fragment(R.layout.fragment_detail) {

    private val CLASS_NAME = "DetailFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

        blog_image.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_finalFragment)
        }

        initUI()
    }

    private fun initUI(){
        uiCommunicationListener.showStatusBar()
        uiCommunicationListener.expandAppBar()
        uiCommunicationListener.hideCategoriesMenu()
    }

    private fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if(viewState != null){
                viewState.detailFragmentView.selectedBlogPost?.let{ selectedBlogPost ->
//                    printLogD(CLASS_NAME, "$selectedBlogPost")
                    setBlogPostToView(selectedBlogPost)
                }
            }
        })
    }

    private fun setBlogPostToView(blogPost: BlogPost){
        requestManager
            .setImage(blogPost.image, blog_image)
        blog_title.text = blogPost.title
        blog_category.text = blogPost.category
        blog_body.text = blogPost.body
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUICommunicationListener(null)
    }

    fun setUICommunicationListener(mockUICommuncationListener: UICommunicationListener?){

        // TEST: Set interface from mock
        if(mockUICommuncationListener != null){
            this.uiCommunicationListener = mockUICommuncationListener
        }
        else{ // PRODUCTION: if no mock, get from MainNavHostFragment
            val navHostFragment = activity?.supportFragmentManager
                ?.findFragmentById(R.id.nav_host_fragment) as MainNavHostFragment?
            navHostFragment?.let{ navHost ->
                this.uiCommunicationListener = navHost.uiCommunicationListener
            }
        }
    }
}


















