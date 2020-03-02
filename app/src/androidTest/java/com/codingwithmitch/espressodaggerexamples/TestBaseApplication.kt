package com.codingwithmitch.espressodaggerexamples

import com.codingwithmitch.espressodaggerexamples.di.DaggerTestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TestBaseApplication : BaseApplication(){

    override fun initAppComponent() {
        appComponent = DaggerTestAppComponent.builder()
            .application(this)
            .build()
    }
}