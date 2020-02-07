package com.codingwithmitch.espressodaggerexamples

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@UseExperimental(InternalCoroutinesApi::class)
class TestBaseApplication : BaseApplication(){

    override fun initAppComponent() {
        // do nothing. TestAppComponent will be initialized in tests
    }
}










