package com.codingwithmitch.espressodaggerexamples.fragments

import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.ListFragment
import com.codingwithmitch.espressodaggerexamples.ui.UICommunicationListener
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MockFragmentFactory(
    private val viewModelFactory: MainViewModelFactory,
    private val uiCommunicationListener: UICommunicationListener
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            ListFragment::class.java.name -> {
                val fragment = ListFragment(viewModelFactory)
                fragment.setUICommunicationListener(uiCommunicationListener)
                fragment
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}