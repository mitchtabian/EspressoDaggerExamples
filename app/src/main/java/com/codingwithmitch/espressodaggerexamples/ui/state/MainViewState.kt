package com.codingwithmitch.espressodaggerexamples.ui.state

import android.os.Parcelable
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.ViewState
import kotlinx.android.parcel.Parcelize


const val MAIN_VIEW_STATE_BUNDLE_KEY = "com.codingwithmitch.espressodaggerexamples.ui.state.MainViewState"

@Parcelize
data class MainViewState (

    var listFragmentView: ListFragmentView = ListFragmentView(),

    var detailFragmentView: DetailFragmentView = DetailFragmentView(),

    var finalFragmentView: FinalFragmentView =  FinalFragmentView()
) : Parcelable, ViewState {

    @Parcelize
    data class ListFragmentView(

        var blogs: List<BlogPost> = ArrayList(),
        var categories: List<Category> = ArrayList()
    ) : Parcelable

    @Parcelize
    data class DetailFragmentView(

        var selectedBlogPost: BlogPost? = null
    ) : Parcelable

    @Parcelize
    data class FinalFragmentView(

        var imageUrl: String? = null
    ) : Parcelable

}

