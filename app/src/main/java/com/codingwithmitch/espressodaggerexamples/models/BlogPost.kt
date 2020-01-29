package com.codingwithmitch.espressodaggerexamples.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BlogPost (

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
) : Parcelable {
    override fun toString(): String {
        return "\nBlogPost(" +
                "\npk=$pk" +
                "\ntitle='$title'" +
                "\nbody='$body'" +
                "\nimage='$image'" +
                "\ncategory='$category'" +
                "\n)" +
                "\n----------"
    }
}