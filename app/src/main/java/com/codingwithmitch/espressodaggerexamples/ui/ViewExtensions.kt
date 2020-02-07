package com.codingwithmitch.espressodaggerexamples.ui

import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.codingwithmitch.espressodaggerexamples.R

fun Fragment.displayErrorDialog(errorMessage: String?){
    MaterialDialog(this.requireContext())
        .show{
            title(R.string.text_error)
            message(text = errorMessage)
            positiveButton(R.string.text_ok)
        }
}