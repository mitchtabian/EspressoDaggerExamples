package com.codingwithmitch.espressodaggerexamples.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.codingwithmitch.daggermultifeature.feature1.di.keys.MainFragmentKey
import com.codingwithmitch.espressodaggerexamples.fragments.MainFragmentFactory
import com.codingwithmitch.espressodaggerexamples.ui.DetailFragment
import com.codingwithmitch.espressodaggerexamples.ui.FinalFragment
import com.codingwithmitch.espressodaggerexamples.ui.ListFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

/* Alternative is provided for test (TestFragmentModule) */
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class FragmentModule {

    @Binds
    abstract fun bindFragmentFactory(fragmentFactory: MainFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @MainFragmentKey(ListFragment::class)
    abstract fun bindListFragment(fragment: ListFragment): Fragment

    @Binds
    @IntoMap
    @MainFragmentKey(DetailFragment::class)
    abstract fun bindDetailFragment(fragment: DetailFragment): Fragment

    @Binds
    @IntoMap
    @MainFragmentKey(FinalFragment::class)
    abstract fun bindFinalFragment(fragment: FinalFragment): Fragment

}















