package com.codingwithmitch.espressodaggerexamples.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BlogPost (

    @SerializedName("pk")
    @Expose
    var pk: Int,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("body")
    @Expose
    var body: String,

    @SerializedName("image")
    @Expose
    var image: String,

    @SerializedName("category")
    @Expose
    var category: String
)