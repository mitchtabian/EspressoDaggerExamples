package com.codingwithmitch.espressodaggerexamples.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.codingwithmitch.espressodaggerexamples.BaseApplication

import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_final.*
import java.lang.ClassCastException
import javax.inject.Inject

class FinalFragment
@Inject
constructor(
    private val viewModelFactory: MainViewModelFactory
)
: Fragment(R.layout.fragment_final) {

    private val CLASS_NAME = "DetailFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        uiCommunicationListener.hideStatusBar()
    }

    private fun subscribeObservers(){
        uiCommunicationListener.displayMainProgressBar(isLoading = true)

        viewModel.selectedBlog.observe(viewLifecycleOwner, Observer { blogPost ->

            if(blogPost != null){
                uiCommunicationListener.displayMainProgressBar(isLoading = false)
                setImage(blogPost.image)
            }
        })
    }

    private fun setImage(imageUrl: String){
        this.view?.let { view ->
            Glide.with(view)
                .load(imageUrl)
                .into(scaling_image_view)
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
