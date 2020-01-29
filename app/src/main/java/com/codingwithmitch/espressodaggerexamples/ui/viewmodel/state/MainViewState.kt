package com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state

import android.os.Parcelable
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.Event
import com.codingwithmitch.espressodaggerexamples.util.ViewState
import kotlinx.android.parcel.Parcelize


const val MAIN_VIEW_STATE_BUNDLE_KEY = "com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState"

@Parcelize
data class MainViewState (

    var activeJobCounter: HashSet<String> = HashSet(),

    var errorMessage: Event<String>? = null,

    var listFragmentView: ListFragmentView = ListFragmentView(),

    var detailFragmentView: DetailFragmentView = DetailFragmentView()

) : Parcelable, ViewState {

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

