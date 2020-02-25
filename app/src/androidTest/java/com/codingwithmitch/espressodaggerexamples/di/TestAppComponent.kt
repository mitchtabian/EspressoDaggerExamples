package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.fragments.MainNavHostFragment
import com.codingwithmitch.espressodaggerexamples.ui.*
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(modules = [
//    TestFragmentModule::class,
    FragmentModule::class,
    TestViewModelModule::class,
    TestRepositoryModule::class,
    TestAppModule::class
])
interface TestAppComponent: AppComponent {

    val apiService: FakeApiService

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(listFragmentTest: ListFragmentTests)

    fun inject(detailFragmentTest: DetailFragmentTest)

    fun inject(finalFragmentTest: FinalFragmentTest)

    fun inject(listFragmentErrorTests: ListFragmentErrorTests)

}














