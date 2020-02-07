package com.codingwithmitch.espressodaggerexamples.util

import android.widget.ImageView

interface GlideManager {

    fun setImage(imageUrl: String, imageView: ImageView)
}