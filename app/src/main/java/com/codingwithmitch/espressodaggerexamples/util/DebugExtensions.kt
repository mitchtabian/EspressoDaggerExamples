package com.codingwithmitch.espressodaggerexamples.util

import android.util.Log

private val TAG: String = "AppDebug"

const val DEBUG = true

fun printLogD(className: String?, message: String ) {
    if (DEBUG) {
        Log.d(TAG, "$className: $message")
    }
}