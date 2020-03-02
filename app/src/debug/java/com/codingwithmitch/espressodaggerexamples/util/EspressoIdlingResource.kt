package com.codingwithmitch.espressodaggerexamples.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private val CLASS_NAME = "EspressoIdlingResource"

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        printLogD(CLASS_NAME, "INCREMENTING.")
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            printLogD(CLASS_NAME, "DECREMENTING.")
            countingIdlingResource.decrement()
        }
    }

    fun clear(): Boolean {
        if (!countingIdlingResource.isIdleNow) {
            decrement()
            return false
        }
        else{
            return true
        }
    }
}