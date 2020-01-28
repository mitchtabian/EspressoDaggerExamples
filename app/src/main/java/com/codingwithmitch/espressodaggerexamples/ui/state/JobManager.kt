package com.codingwithmitch.espressodaggerexamples.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingwithmitch.espressodaggerexamples.util.ApiResult
import com.codingwithmitch.espressodaggerexamples.util.DataState
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class JobManager<ViewState>
constructor(
    private val scope: CoroutineScope
){

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val jobs: LinkedHashMap<MainStateEvent, Job> = LinkedHashMap()


    private fun executeJob(stateEvent: MainStateEvent, jobFunction: suspend () -> DataState<MainViewState>){
        jobs[stateEvent] = scope.launch { jobFunction.invoke() }
    }

    fun addJob(stateEvent: MainStateEvent, jobFunction: suspend () -> DataState<MainViewState>){
        cancelJob(stateEvent)
        executeJob(stateEvent, jobFunction)

        val flowA = flowOf(1, 2, 3)
            .map { it + 1 } // Will be executed in ctxA
            .flowOn(IO + Job())
    }

    fun cancelJob(stateEvent: MainStateEvent){
        val job = jobs[stateEvent]
        if(job != null){
            job.cancel()
            jobs.remove(stateEvent)
        }
    }

}













