package com.codingwithmitch.espressodaggerexamples

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.di.AppComponent
import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@UseExperimental(InternalCoroutinesApi::class)
class TestBaseApplication : Application(){

    lateinit var appComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun initAppComponent() {
        appComponent = DaggerTestAppComponent.builder()
            .application(this)
            .build()
    }
}










