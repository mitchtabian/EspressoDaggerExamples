package com.codingwithmitch.espressodaggerexamples.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.MainViewModel
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.ClassCastException
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class DetailFragment
@Inject
constructor(
    private val viewModelFactory: MainViewModelFactory
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
        view?.run {
            Glide.with(this)
                .load(blogPost.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(blog_image)
            blog_title.text = blogPost.title
            blog_category.text = blogPost.category
            blog_body.text = blogPost.body
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as BaseApplication)
            .appComponent
            .inject(this)
        super.onAttach(context)
    }

    fun setUICommunicationListener(uiCommunicationListener: UICommunicationListener){
        this.uiCommunicationListener = uiCommunicationListener
    }
}


















