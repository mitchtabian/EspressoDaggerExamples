package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.repository.FakeMainRepositoryImpl
import com.codingwithmitch.espressodaggerexamples.ui.DetailFragmentTest
import com.codingwithmitch.espressodaggerexamples.ui.ListFragmentErrorTests
import com.codingwithmitch.espressodaggerexamples.ui.ListFragmentIntegrationTests
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(modules = [
    TestFragmentModule::class,
    TestViewModelModule::class,
    TestAppModule::class
])
interface TestAppComponent : AppComponent{

    val apiService: FakeApiService

    val mainRepository: FakeMainRepositoryImpl

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(detailFragmentTest: DetailFragmentTest)

    fun inject(listFragmentIntegrationTests: ListFragmentIntegrationTests)

    fun inject(listFragmentErrorTests: ListFragmentErrorTests)
}


















