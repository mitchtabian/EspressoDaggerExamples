package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.ui.ListFragmentTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@UseExperimental(InternalCoroutinesApi::class)
@Singleton
@Component(modules = [
    FragmentModule::class,
    ViewModelModule::class,
    FakeAppModule::class
])
interface TestAppComponent: AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(listFragmentTest: ListFragmentTest)

}