package com.codingwithmitch.espressodaggerexamples.util

object Constants{

    const val TAG = "AppDebug" // Tag for logs
    const val DEBUG = true // enable logging


    const val UNKNOWN_ERROR = "Unknown error"

    const val NETWORK_TIMEOUT = 2000L
    const val NETWORK_ERROR = "Network error"
    const val NETWORK_ERROR_TIMEOUT = "Network timeout"
    const val NETWORK_DELAY = 3000L // ms

    enum class ApplicationMode {
        NORMAL, TESTING
    }

}