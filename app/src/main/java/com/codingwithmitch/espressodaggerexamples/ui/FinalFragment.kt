package com.codingwithmitch.espressodaggerexamples.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingwithmitch.espressodaggerexamples.BaseApplication

import com.codingwithmitch.espressodaggerexamples.R
import javax.inject.Inject

class FinalFragment
@Inject
constructor()
: Fragment(R.layout.fragment_final) {

    private val TAG: String = "AppDebug"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        (activity?.application as BaseApplication)
            .getAppComponent()
            .inject(this)
        super.onAttach(context)
    }
}
