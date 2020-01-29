package com.codingwithmitch.espressodaggerexamples.util

import android.os.Parcelable
import com.codingwithmitch.espressodaggerexamples.repository.UNKNOWN_ERROR
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class DataState<T>(
    var errorEvent: Event<String>? = null,
    var dataEvent: Event<T>? = null
) {

    companion object {

        fun <T> error(
            errorMessage: String? = UNKNOWN_ERROR
        ): DataState<T> {
            return DataState(
                errorEvent = Event.errorEvent(errorMessage),
                dataEvent = null
            )
        }

        fun <T> data(
            data: T? = null
        ): DataState<T> {
            return DataState(
                errorEvent = null,
                dataEvent = Event.dataEvent(data)
            )
        }
    }
}


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
@Parcelize
open class Event<out T>(
    private val content: @RawValue T
) : Parcelable {

    @IgnoredOnParcel
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