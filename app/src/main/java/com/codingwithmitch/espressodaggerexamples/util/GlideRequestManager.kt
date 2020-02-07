package com.codingwithmitch.espressodaggerexamples.util

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.codingwithmitch.espressodaggerexamples.util.Constants.ApplicationMode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlideRequestManager
@Inject
constructor(
    private val requestManager: RequestManager,
    private val applicationMode: ApplicationMode
){

    fun setImage(imageUrl: String, imageView: ImageView){
        if(applicationMode == ApplicationMode.NORMAL){
            requestManager
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }
}