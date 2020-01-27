package com.codingwithmitch.espressodaggerexamples.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.codingwithmitch.espressodaggerexamples.util.ApiResult
import com.codingwithmitch.espressodaggerexamples.util.DataState
import com.codingwithmitch.espressodaggerexamples.repository.NETWORK_ERROR
import com.codingwithmitch.espressodaggerexamples.util.ViewState
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private val CLASS_NAME = "MainViewModel"

fun <T> MainViewModel.launchJob(
    dataObj: MutableLiveData<DataState<T>>,
    repositoryCall: suspend () -> ApiResult<T>
){
    viewModelScope.launch {

        dataObj.value = DataState.loading(isLoading = true)

        val startTime = System.currentTimeMillis()
        printLogD(CLASS_NAME, "Starting job...")
        val response = repositoryCall.invoke()
        printLogD(
            "MainViewModel",
            "Time elapsed: ${(System.currentTimeMillis() - startTime)} ms."
        )
        when(response){

            is ApiResult.Success -> {
                printLogD(CLASS_NAME, "Success.")
                dataObj.value = DataState.data(response.value)
            }

            is ApiResult.GenericError -> {
                printLogD(CLASS_NAME, "GENERIC ERROR: ${response.errorMessage}")
                dataObj.value = DataState.error(response.errorMessage)
            }

            is ApiResult.NetworkError -> {
                printLogD(CLASS_NAME, "NETWORK ERROR")
                dataObj.value = DataState.error(NETWORK_ERROR)
            }
        }

        dataObj.value = DataState.loading(isLoading = false)
    }
}


fun <T> MainViewModel.launchLiveDataJob(
    repositoryCall: suspend () -> ApiResult<T>
): LiveData<DataState<T>> {
    return liveData {
        emit(DataState.loading(isLoading = true))

        val startTime = System.currentTimeMillis()
        printLogD(CLASS_NAME, "Starting job...")
        val response = repositoryCall.invoke()
        printLogD(
            "MainViewModel",
            "Time elapsed: ${(System.currentTimeMillis() - startTime)} ms."
        )
        emit(when(response){

            is ApiResult.Success -> {
                printLogD(CLASS_NAME, "Success.")
                DataState.data(response.value)
            }

            is ApiResult.GenericError -> {
                printLogD(CLASS_NAME, "GENERIC ERROR: ${response.errorMessage}")
                DataState.error(response.errorMessage)
            }

            is ApiResult.NetworkError -> {
                printLogD(CLASS_NAME, "NETWORK ERROR")
                DataState.error(NETWORK_ERROR)
            }
        })

        emit(DataState.loading(isLoading = false))
    }
}


























