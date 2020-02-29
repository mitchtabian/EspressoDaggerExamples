package com.codingwithmitch.espressodaggerexamples.fragments

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.TestBaseApplication
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import com.codingwithmitch.espressodaggerexamples.ui.UICommunicationListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@UseExperimental(InternalCoroutinesApi::class)
class FakeNavHostFragment: NavHostFragment(){

    private val TAG: String = "AppDebug"

//    @Inject
    lateinit var mainFragmentFactory: FakeMainFragmentFactory

    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onAttach(context: Context) {
//        ((activity?.application as TestBaseApplication).appComponent as TestAppComponent)
//            .inject(this)
        childFragmentManager.fragmentFactory = mainFragmentFactory
        super.onAttach(context)
    }

    fun setUICommunicationListener(uiCommunicationListener: UICommunicationListener){
        this.uiCommunicationListener = uiCommunicationListener
    }

}







