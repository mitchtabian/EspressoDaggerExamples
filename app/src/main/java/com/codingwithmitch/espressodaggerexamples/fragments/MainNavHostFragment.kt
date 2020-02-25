package com.codingwithmitch.espressodaggerexamples.fragments

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.di.AppComponent
import com.codingwithmitch.espressodaggerexamples.ui.UICommunicationListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainNavHostFragment : NavHostFragment(){

    private val TAG: String = "AppDebug"

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onAttach(context: Context) {
        ((activity?.application as BaseApplication).appComponent as AppComponent)
            .inject(this)
        childFragmentManager.fragmentFactory = mainFragmentFactory
        super.onAttach(context)
    }

    fun setUICommunicationListener(uiCommunicationListener: UICommunicationListener){
        this.uiCommunicationListener = uiCommunicationListener
    }
}









