package com.codingwithmitch.espressodaggerexamples.ui.viewmodel

import android.os.Parcelable
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.ErrorStack
import com.codingwithmitch.espressodaggerexamples.util.ErrorState
import com.codingwithmitch.espressodaggerexamples.util.EspressoIdlingResource
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setBlogListData(blogList: List<BlogPost>){
    val update = getCurrentViewStateOrNew()
    update.listFragmentView.blogs = blogList
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setCategoriesData(categories: List<Category>){
    val update = getCurrentViewStateOrNew()
    update.listFragmentView.categories = categories
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setSelectedBlogPost(blogPost: BlogPost){
    val update = getCurrentViewStateOrNew()
    update.detailFragmentView.selectedBlogPost = blogPost
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.clearActiveJobCounter(){
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.clear()
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.addJobToCounter(stateEventName: String){
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.add(stateEventName)
    setViewState(update)
    EspressoIdlingResource.increment()
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.removeJobFromCounter(stateEventName: String){
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.remove(stateEventName)
    setViewState(update)
    EspressoIdlingResource.decrement()
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.clearBlogPosts(){
    val update = getCurrentViewStateOrNew()
    update.listFragmentView.blogs = null
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setLayoutManagerState(layoutManagerState: Parcelable){
    val update = getCurrentViewStateOrNew()
    update.listFragmentView.layoutManagerState = layoutManagerState
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.clearLayoutManagerState(){
    val update = getCurrentViewStateOrNew()
    update.listFragmentView.layoutManagerState = null
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.appendErrorState(errorState: ErrorState){
    errorStack.add(errorState)
    printLogD(CLASS_NAME, "Appending error state. stack size: ${errorStack.size}")
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.clearError(index: Int){
    errorStack.removeAt(index)
}


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setErrorStack(errorStack: ErrorStack){
    this.errorStack.addAll(errorStack)
}









