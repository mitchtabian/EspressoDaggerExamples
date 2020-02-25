package com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state

import android.os.Parcelable
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import kotlinx.android.parcel.Parcelize


const val MAIN_VIEW_STATE_BUNDLE_KEY = "com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState"

@Parcelize
data class MainViewState (

    var activeJobCounter: HashSet<String> = HashSet(),

    var listFragmentView: ListFragmentView = ListFragmentView(),

    var detailFragmentView: DetailFragmentView = DetailFragmentView()

) : Parcelable {

    @Parcelize
    data class ListFragmentView(

        var blogs: List<BlogPost>? = null,
        var categories: List<Category>? = null,
        var layoutManagerState: Parcelable? = null

    ) : Parcelable

    @Parcelize
    data class DetailFragmentView(

        var selectedBlogPost: BlogPost? = null

    ) : Parcelable


}

