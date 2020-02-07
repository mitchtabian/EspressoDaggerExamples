package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.ui.DetailFragmentTest
import com.codingwithmitch.espressodaggerexamples.ui.ListFragmentTests
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
    TestAppModule::class,
    TestRepositoryModule::class
])
interface TestAppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(app: Application): Builder

        fun repositoryModule(repositoryModule: TestRepositoryModule): Builder

        fun build(): TestAppComponent
    }

    fun inject(listFragmentTest: ListFragmentTests)

    fun inject(detailFragmentTest: DetailFragmentTest)

}














