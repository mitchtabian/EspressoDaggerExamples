package com.codingwithmitch.espressodaggerexamples.ui.viewmodel

import androidx.lifecycle.*
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent.*
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.DataState
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@UseExperimental(FlowPreview::class)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModel
@Inject
constructor(
    val mainRepository: MainRepository
) :ViewModel() {

    private val CLASS_NAME = "MainViewModel"

    private val dataChannel = ConflatedBroadcastChannel<DataState<MainViewState>>()

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    init {
        setupChannel()
    }

    private fun setupChannel(){
        dataChannel
            .asFlow()
            .onEach{ dataState ->
                dataState.dataEvent?.getContentIfNotHandled()?.let { data ->
                    handleNewData(data)
                }
                dataState.errorEvent?.getContentIfNotHandled()?.let { errorMessage ->
                    setErrorMessage(errorMessage)
                }
                dataState.stateEventName?.let { eventName ->
                    removeJobFromCounter(eventName)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun offerToDataChannel(dataState: DataState<MainViewState>){
        if(!dataChannel.isClosedForSend){
            dataChannel.offer(dataState)
        }
    }

    fun setStateEvent(stateEvent: MainStateEvent){
        when(stateEvent){
            is GetAllBlogs -> {
                launchJob(
                    stateEvent.toString(),
                    mainRepository.getAllBlogs()
                )
            }

            is GetCategories -> {
                launchJob(
                    stateEvent.toString(),
                    mainRepository.getCategories()
                )
            }

            is SearchBlogsByCategory -> {
                launchJob(
                    stateEvent.toString(),
                    mainRepository.getBlogs(stateEvent.category)
                )
            }
        }
    }

    private fun launchJob(stateEventName: String, jobFunction: Flow<DataState<MainViewState>>){
        if(!isJobAlreadyActive(stateEventName)){
            addJobToCounter(stateEventName)
            jobFunction
                .onEach { dataState ->
                    offerToDataChannel(dataState)
                }
                .launchIn(viewModelScope)
        }
    }

    fun setViewState(viewState: MainViewState){
        _viewState.value = viewState
    }

    fun handleNewData(data: MainViewState){

        data.listFragmentView.blogs?.let { blogs ->
            setBlogListData(blogs)
        }

        data.listFragmentView.categories?.let { categories ->
            setCategoriesData(categories)
        }

        data.detailFragmentView.selectedBlogPost?.let { blogPost ->
            setSelectedBlogPost(blogPost)
        }

    }


}



















