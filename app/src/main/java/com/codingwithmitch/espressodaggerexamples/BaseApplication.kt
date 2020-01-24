package com.codingwithmitch.espressodaggerexamples

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.di.AppComponent
import com.codingwithmitch.espressodaggerexamples.di.DaggerAppComponent

class BaseApplication: Application() {

    private val TAG: String = "AppDebug"

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    fun getAppComponent(): AppComponent {
        return appComponent
    }

}




