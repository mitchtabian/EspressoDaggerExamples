package com.codingwithmitch.espressodaggerexamples.ui.viewmodel

import androidx.lifecycle.*
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent.*
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.*
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
    private val mainRepository: MainRepository
) :ViewModel() {

    val CLASS_NAME = "MainViewModel"

    private val dataChannel = ConflatedBroadcastChannel<DataState<MainViewState>>()

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val errorStack = ErrorStack()

    val errorState: LiveData<ErrorState> = errorStack.errorState

    val viewState: LiveData<MainViewState>
        get() = _viewState

    init {
        setupChannel()
    }

    private fun setupChannel(){
        dataChannel
            .asFlow()
            .onEach{ dataState ->
                dataState.data?.let { data ->
                    handleNewData(dataState.stateEvent, data)
                }
                dataState.error?.let { error ->
                    handleNewError(dataState.stateEvent, error)
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
                    stateEvent,
                    mainRepository.getAllBlogs(stateEvent)
                )
            }

            is GetCategories -> {
                launchJob(
                    stateEvent,
                    mainRepository.getCategories(stateEvent)
                )
            }

            is SearchBlogsByCategory -> {
                launchJob(
                    stateEvent,
                    mainRepository.getBlogs(stateEvent, stateEvent.category)
                )
            }
        }
    }

    private fun handleNewError(stateEvent: StateEvent, error: ErrorState) {
        appendErrorState(error)
        removeJobFromCounter(stateEvent.toString())
    }

    fun handleNewData(stateEvent: StateEvent, data: MainViewState){

        data.listFragmentView.blogs?.let { blogs ->
            setBlogListData(blogs)
        }

        data.listFragmentView.categories?.let { categories ->
            setCategoriesData(categories)
        }

        data.detailFragmentView.selectedBlogPost?.let { blogPost ->
            setSelectedBlogPost(blogPost)
        }

        removeJobFromCounter(stateEvent.toString())
    }

    private fun launchJob(stateEvent: StateEvent, jobFunction: Flow<DataState<MainViewState>>){
        if(!isJobAlreadyActive(stateEvent.toString())){
            addJobToCounter(stateEvent.toString())
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


}



















