package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.ui.ListFragmentEmptyDataTest
import com.codingwithmitch.espressodaggerexamples.ui.ListFragmentRealDataTest
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
    AppModule::class,
    TestDataModule::class
])
interface TestAppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(listFragmentTest: ListFragmentRealDataTest)

    fun inject(listFragmentTest: ListFragmentEmptyDataTest)

}














