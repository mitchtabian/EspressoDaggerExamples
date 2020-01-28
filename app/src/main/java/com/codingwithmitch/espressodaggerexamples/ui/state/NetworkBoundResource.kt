package com.codingwithmitch.espressodaggerexamples.ui.state

import androidx.lifecycle.MutableLiveData
import com.codingwithmitch.espressodaggerexamples.repository.NETWORK_DELAY
import com.codingwithmitch.espressodaggerexamples.repository.UNKNOWN_ERROR
import com.codingwithmitch.espressodaggerexamples.util.DataState
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

abstract class NetworkBoundResource<ViewState, DataType>
constructor(
    scope: CoroutineScope
) {

    val result: MutableLiveData<DataState<ViewState>> = MutableLiveData()

    init {
        scope.launch {
            delay(NETWORK_DELAY)
            try {
                val response = getApiRequest()
                onSuccess(response)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        onNetworkError()
                    }
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        onGenericError(errorResponse?: UNKNOWN_ERROR)
                    }
                    else -> {
                        onGenericError(UNKNOWN_ERROR)
                    }
                }
            }
        }
    }

    abstract suspend fun getApiRequest(): DataType

    abstract fun onSuccess(data: DataType)

    abstract fun onGenericError(errorMessage: String)

    abstract fun onNetworkError()

    fun convertErrorBody(throwable: HttpException): String? {
        return try {
            throwable.response()?.errorBody()?.toString()
        } catch (exception: Exception) {
            UNKNOWN_ERROR
        }
    }

    fun getAsLiveData() = result
}









