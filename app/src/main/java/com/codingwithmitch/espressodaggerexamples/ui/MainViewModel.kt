package com.codingwithmitch.espressodaggerexamples.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.util.DataState
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    val mainRepository: MainRepository
) :ViewModel(){

    private val _blogs: MutableLiveData<DataState<List<BlogPost>>> = MutableLiveData()

    val blogs: LiveData<DataState<List<BlogPost>>>
        get() = _blogs

    fun getBlogPosts(){
        launchJob(_blogs){mainRepository.getBlogs("")}
    }

}



















