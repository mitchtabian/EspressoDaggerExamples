package com.codingwithmitch.espressodaggerexamples.di

import androidx.fragment.app.Fragment
import com.codingwithmitch.espressodaggerexamples.ui.DetailFragment
import com.codingwithmitch.espressodaggerexamples.ui.FinalFragment
import com.codingwithmitch.espressodaggerexamples.ui.ListFragment
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class TestFragmentModule {

    @Binds
    abstract fun bindListFragment(fragment: ListFragment): Fragment

    @Binds
    abstract fun bindDetailFragment(fragment: DetailFragment): Fragment

    @Binds
    abstract fun bindFinalFragment(fragment: FinalFragment): Fragment

}
















