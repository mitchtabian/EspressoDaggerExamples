package com.codingwithmitch.espressodaggerexamples.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailFragment
@Inject
constructor(
    private val viewModelFactory: MainViewModelFactory
) : Fragment(R.layout.fragment_detail) {

    private val TAG: String = "AppDebug"

    val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_finalFragment)
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as BaseApplication)
            .getAppComponent()
            .inject(this)
        super.onAttach(context)
    }
}


















