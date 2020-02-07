package com.codingwithmitch.espressodaggerexamples.fragments

import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.*
import com.codingwithmitch.espressodaggerexamples.util.GlideManager
import com.codingwithmitch.espressodaggerexamples.viewmodels.FakeMainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FakeMainFragmentFactory
constructor(
    private val viewModelFactory: FakeMainViewModelFactory,
    private val uiCommunicationListener: UICommunicationListener,
    private val requestManager: GlideManager
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            ListFragment::class.java.name -> {
                val fragment = ListFragment(viewModelFactory, requestManager)
                fragment.setUICommunicationListener(uiCommunicationListener)
                fragment
            }

            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(viewModelFactory, requestManager)
                fragment.setUICommunicationListener(uiCommunicationListener)
                fragment
            }

            FinalFragment::class.java.name -> {
                val fragment = FinalFragment(viewModelFactory, requestManager)
                fragment.setUICommunicationListener(uiCommunicationListener)
                fragment
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}