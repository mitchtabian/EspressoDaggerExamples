package com.codingwithmitch.espressodaggerexamples.util

import androidx.lifecycle.MutableLiveData
import kotlinx.android.parcel.IgnoredOnParcel
import java.lang.IndexOutOfBoundsException

const val ERROR_STACK_BUNDLE_KEY = "com.codingwithmitch.espressodaggerexamples.util.ErrorStack"


class ErrorStack: ArrayList<ErrorState>() {

    @IgnoredOnParcel
    val errorState: MutableLiveData<ErrorState> = MutableLiveData()

    override fun addAll(elements: Collection<ErrorState>): Boolean {
        for(element in elements){
            add(element)
        }
        return true // always return true. We don't care about result bool.
    }

    override fun add(element: ErrorState): Boolean {
        if(this.size == 0){
            setErrorState(errorState = element)
        }
        if(this.contains(element)){ // prevent duplicate errors added to stack
            return false
        }
        return super.add(element)
    }

    override fun removeAt(index: Int): ErrorState {
        try{
            val transaction = super.removeAt(index)
            if(this.size > 0){
                setErrorState(errorState = this[0])
            }
            else{
                setErrorState(null)
            }
            return transaction
        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }
        return ErrorState("does nothing") // this does nothing
    }

    private fun setErrorState(errorState: ErrorState?){
        this.errorState.value = errorState
    }
}