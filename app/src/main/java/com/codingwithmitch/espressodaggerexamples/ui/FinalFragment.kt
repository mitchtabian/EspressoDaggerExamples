package com.codingwithmitch.espressodaggerexamples.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codingwithmitch.espressodaggerexamples.BaseApplication

import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.fragments.MainNavHostFragment
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.MainViewModel
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_final.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.ClassCastException
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FinalFragment
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager
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
        uiCommunicationListener.hideStatusBar()
    }

    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if(viewState != null){
                viewState.detailFragmentView.selectedBlogPost?.let{ blogPost ->
                    setImage(blogPost.image)
                }
            }
        })
    }

    private fun setImage(imageUrl: String){
        requestManager.setImage(imageUrl, scaling_image_view)
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
        else{ // PRODUCTION: if no mock, get from context
            try {
                uiCommunicationListener = (context as UICommunicationListener)
            }catch (e: Exception){
                Log.e(CLASS_NAME, "$context must implement UICommunicationListener")
            }
        }
    }
}