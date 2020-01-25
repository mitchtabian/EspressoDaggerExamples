package com.codingwithmitch.espressodaggerexamples.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.util.DataState
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import kotlinx.coroutines.cancel
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    val mainRepository: MainRepository
) :ViewModel(){

    private val CLASS_NAME = "MainViewModel"

    private val _categories: MutableLiveData<DataState<List<Category>>> = MutableLiveData()
    private val _blogs: MutableLiveData<DataState<List<BlogPost>>> = MutableLiveData()
    private val _selectedBlog: MutableLiveData<BlogPost> = MutableLiveData()

    val blogs: LiveData<DataState<List<BlogPost>>>
        get() = _blogs

    val selectedBlog: LiveData<BlogPost>
        get() = _selectedBlog

    val categories: LiveData<DataState<List<Category>>>
        get() = _categories

    fun getBlogPosts(category: String){
        launchJob(_blogs){mainRepository.getBlogs(category)}
    }

    fun getAllBlogs(){
        launchJob(_blogs){mainRepository.getAllBlogs()}
    }

    fun getCategories(){
        launchJob(_categories){mainRepository.getCategories()}
    }

    fun setSelectedBlogPost(blogPost: BlogPost){
        printLogD(CLASS_NAME, "setSelectedBlogPost: ${blogPost}")
        _selectedBlog.value = blogPost
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}



















