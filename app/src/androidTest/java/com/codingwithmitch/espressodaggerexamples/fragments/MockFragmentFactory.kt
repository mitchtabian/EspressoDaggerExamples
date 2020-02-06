package com.codingwithmitch.espressodaggerexamples.fragments

import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.*
import com.codingwithmitch.espressodaggerexamples.util.GlideRequestManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MockFragmentFactory
//@Inject
constructor(
    private val viewModelFactory: MockMainViewModelFactory,
    private val uiCommunicationListener: UICommunicationListener,
    private val requestManager: GlideRequestManager
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            ListFragment::class.java.name -> {
                val fragment = ListFragment(viewModelFactory, requestManager)
                fragment.setUICommunicationListener(uiCommunicationListener)
                fragment
            }

            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(viewModelFactory)
                fragment.setUICommunicationListener(uiCommunicationListener)
                fragment
            }

            FinalFragment::class.java.name -> {
                val fragment = FinalFragment(viewModelFactory)
                fragment.setUICommunicationListener(uiCommunicationListener)
                fragment
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}