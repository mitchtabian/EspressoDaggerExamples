package com.codingwithmitch.espressodaggerexamples.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.util.Event
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import java.lang.ClassCastException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListFragment
@Inject
constructor(
    private val viewModelFactory: MainViewModelFactory
) : Fragment(R.layout.fragment_list) {

    private val CLASS_NAME = "ListFragment"

    lateinit var dataStateListener: DataStateListener

    val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        go.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        }

        subscribeObservers()

        viewModel.getAllBlogs()
//        viewModel.getBlogPosts("fun")
    }

    private fun subscribeObservers(){
        viewModel.blogs.observe(viewLifecycleOwner, Observer { dataState ->

            if(dataState != null){
                dataStateListener.onDataStateChange(dataState)

                dataState.data?.let { dataEvent ->
                    handleIncomingBlogPosts(dataEvent)
                }
            }
        })
    }

    private fun handleIncomingBlogPosts(dataEvent: Event<List<BlogPost>>){
        dataEvent.getContentIfNotHandled()?.let { blogs ->
            for(blog in blogs){
                printLogD(CLASS_NAME, blog.toString())
            }
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as BaseApplication)
            .getAppComponent()
            .inject(this)
        super.onAttach(context)

        try{
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            printLogD(CLASS_NAME, "$context must implement DataStateListener")
        }
    }
}





















