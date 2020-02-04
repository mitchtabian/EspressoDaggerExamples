package com.codingwithmitch.espressodaggerexamples.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.ui.UICommunicationListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class MainNavHostFragment
@Inject
constructor(
): NavHostFragment(){

    private val TAG: String = "AppDebug"

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onAttach(context: Context) {
        ((activity?.application) as BaseApplication)
            .appComponent
            .inject(this)
        childFragmentManager.fragmentFactory = mainFragmentFactory
        super.onAttach(context)
    }

    fun setUICommunicationListener(uiCommunicationListener: UICommunicationListener){
        this.uiCommunicationListener = uiCommunicationListener
    }
}









