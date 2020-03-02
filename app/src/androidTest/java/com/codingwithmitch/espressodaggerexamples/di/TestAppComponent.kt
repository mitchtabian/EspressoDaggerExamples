package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(modules = [
    FragmentModule::class,
    ViewModelModule::class,
    InternalBindingsModule::class,
    AppModule::class,
    RepositoryModule::class
])
interface TestAppComponent : AppComponent{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TestAppComponent
    }

}


















