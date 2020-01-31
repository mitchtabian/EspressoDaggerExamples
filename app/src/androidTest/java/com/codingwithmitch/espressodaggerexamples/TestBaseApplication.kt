package com.codingwithmitch.espressodaggerexamples

import com.codingwithmitch.espressodaggerexamples.di.TestAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@UseExperimental(InternalCoroutinesApi::class)
class TestBaseApplication : BaseApplication(){

    lateinit var testAppComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    override fun initAppComponent() {
//        testAppComponent = DaggerTestAppComponent.builder()
//            .application(this)
//            .build()
    }
}










