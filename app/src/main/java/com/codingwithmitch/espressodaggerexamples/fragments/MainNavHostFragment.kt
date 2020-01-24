package com.codingwithmitch.espressodaggerexamples.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import javax.inject.Inject

class MainNavHostFragment
@Inject
constructor(
): NavHostFragment(){

    private val TAG: String = "AppDebug"

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    override fun onAttach(context: Context) {
        ((activity?.application) as BaseApplication)
            .getAppComponent()
            .inject(this)
        childFragmentManager.fragmentFactory = mainFragmentFactory
        super.onAttach(context)
    }

}








