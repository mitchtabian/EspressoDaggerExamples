package com.codingwithmitch.espressodaggerexamples.util

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.codingwithmitch.espressodaggerexamples.util.Constants.ApplicationMode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeGlideRequestManager
@Inject
constructor(): GlideManager{

    override fun setImage(imageUrl: String, imageView: ImageView){
        // does nothing
    }
}