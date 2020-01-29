package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.util.ApiResult
import com.codingwithmitch.espressodaggerexamples.util.ApiResult.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
private val TAG: String = "AppDebug"

const val UNKNOWN_ERROR = "Unknown error"
const val NETWORK_ERROR = "Network error"
const val NETWORK_DELAY = 1000L // ms

suspend fun <T> Repository.safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ApiResult<T> {
    return withContext(dispatcher) {
        delay(NETWORK_DELAY)
        try {
            Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    GenericError(
                        null,
                        UNKNOWN_ERROR
                    )
                }
            }
        }
    }
}


private fun Repository.convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.toString()
    } catch (exception: Exception) {
        UNKNOWN_ERROR
    }
}











