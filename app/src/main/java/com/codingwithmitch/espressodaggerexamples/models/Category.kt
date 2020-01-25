package com.codingwithmitch.espressodaggerexamples.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(

    @SerializedName("pk")
    @Expose
    var pk: Int,

    @SerializedName("category_name")
    @Expose
    var category_name: String

) : Parcelable {

    override fun toString(): String {
        return "Category(pk=$pk, category='$category_name')"
    }
}