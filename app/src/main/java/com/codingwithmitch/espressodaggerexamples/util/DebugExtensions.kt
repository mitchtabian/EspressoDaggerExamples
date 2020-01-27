package com.codingwithmitch.espressodaggerexamples.util

import android.util.Log
import com.codingwithmitch.espressodaggerexamples.util.Constants.DEBUG
import com.codingwithmitch.espressodaggerexamples.util.Constants.TAG

fun printLogD(className: String?, message: String ) {
    if (DEBUG) {
        Log.d(TAG, "$className: $message")
    }
}