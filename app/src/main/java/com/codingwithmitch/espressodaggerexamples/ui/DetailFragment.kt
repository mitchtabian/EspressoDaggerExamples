package com.codingwithmitch.espressodaggerexamples.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import java.lang.ClassCastException
import javax.inject.Inject
import javax.inject.Singleton

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
        uiCommunicationListener.hideCategoriesMenu()
    }

    private fun subscribeObservers(){
        viewModel.selectedBlog.observe(viewLifecycleOwner, Observer { blogPost ->
            printLogD(CLASS_NAME, "$blogPost")
            if(blogPost != null){
                setBlogPostToView(blogPost)
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
            .getAppComponent()
            .inject(this)
        super.onAttach(context)

        try{
            uiCommunicationListener = context as UICommunicationListener
        }catch (e: ClassCastException){
            printLogD(CLASS_NAME, "$context must implement UICommunicationListener")
        }
    }
}


















