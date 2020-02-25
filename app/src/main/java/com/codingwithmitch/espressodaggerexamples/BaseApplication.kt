package com.codingwithmitch.espressodaggerexamples

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.di.AppComponent
import com.codingwithmitch.espressodaggerexamples.di.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
open class BaseApplication: Application() {

    private val TAG: String = "AppDebug"

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

   open fun initAppComponent() {
       appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}




