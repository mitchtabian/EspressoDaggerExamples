package com.codingwithmitch.espressodaggerexamples.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.espressodaggerexamples.repository.MainRepository
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.MainViewModel
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MockMainViewModelFactory
@Inject
constructor(
    val mainRepository: MainRepository
): ViewModelProvider.NewInstanceFactory() {

    private val CLASS_NAME = "MockMainViewModelFactory"

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}















