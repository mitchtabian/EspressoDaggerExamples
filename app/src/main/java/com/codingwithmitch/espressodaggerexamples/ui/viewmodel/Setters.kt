package com.codingwithmitch.espressodaggerexamples.ui.viewmodel

import android.os.Parcelable
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.Event
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
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.removeJobFromCounter(stateEventName: String){
    val update = getCurrentViewStateOrNew()
    update.activeJobCounter.remove(stateEventName)
    setViewState(update)
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setErrorMessage(errorMessage: String){
    val update = getCurrentViewStateOrNew()
    update.errorMessage = Event(errorMessage)
    setViewState(update)
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












