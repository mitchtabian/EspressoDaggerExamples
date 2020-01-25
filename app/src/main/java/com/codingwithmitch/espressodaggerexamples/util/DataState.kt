package com.codingwithmitch.espressodaggerexamples.util

import com.codingwithmitch.espressodaggerexamples.repository.UNKNOWN_ERROR

data class DataState<T>(
    var errorEvent: Event<String>? = null,
    var loading: Loading = Loading(false),
    var data: Event<T>? = null
) {

    companion object {

        fun <T> error(
            errorMessage: String? = UNKNOWN_ERROR
        ): DataState<T> {
            return DataState(
                errorEvent = Event.errorEvent(errorMessage),
                loading = Loading(false),
                data = null
            )
        }

        fun <T> loading(
            isLoading: Boolean,
            cachedData: T? = null
        ): DataState<T> {
            return DataState(
                errorEvent = null,
                loading = Loading(isLoading),
                data = null
            )
        }

        fun <T> data(
            data: T? = null
        ): DataState<T> {
            return DataState(
                errorEvent = null,
                loading = Loading(false),
                data = Event.dataEvent(data)
            )
        }
    }
}

data class Loading(val isLoading: Boolean)

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun toString(): String {
        return "Event(content=$content, hasBeenHandled=$hasBeenHandled)"
    }

    companion object{

        private val TAG: String = "AppDebug"

        // we don't want an event if the data is null
        fun <T> dataEvent(data: T?): Event<T>?{
            data?.let {
                return Event(it)
            }
            return null
        }

        // we want an unknown error if the error message in empty
        fun errorEvent(errorMessage: String?): Event<String>?{
            return Event(errorMessage?: UNKNOWN_ERROR)
        }
    }

}