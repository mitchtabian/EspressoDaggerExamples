package com.codingwithmitch.espressodaggerexamples.util

import com.codingwithmitch.espressodaggerexamples.util.Constants.NETWORK_ERROR
import kotlinx.coroutines.CoroutineDispatcher

abstract class ApiResponseHandler <ViewState, Data>(
    dispatcher: CoroutineDispatcher,
    response: ApiResult<Data>
){
    val result: DataState<ViewState> = when(response){

        is ApiResult.GenericError -> {
            DataState.error(
                errorMessage = response.errorMessage,
                stateEventName = stateEventName()
            )
        }

        is ApiResult.NetworkError -> {
            DataState.error(
                NETWORK_ERROR,
                stateEventName = stateEventName()
            )
        }

        is ApiResult.Success -> {
            handleSuccess(resultObj = response.value)
        }
    }

    abstract fun handleSuccess(resultObj: Data): DataState<ViewState>

    abstract fun stateEventName(): String
}