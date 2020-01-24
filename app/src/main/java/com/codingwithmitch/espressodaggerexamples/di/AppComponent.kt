package com.codingwithmitch.espressodaggerexamples.di

import android.app.Application
import com.codingwithmitch.espressodaggerexamples.fragments.MainNavHostFragment
import com.codingwithmitch.espressodaggerexamples.ui.DetailFragment
import com.codingwithmitch.espressodaggerexamples.ui.FinalFragment
import com.codingwithmitch.espressodaggerexamples.ui.ListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    FragmentModule::class,
    ViewModelModule::class,
    AppModule::class
])
interface AppComponent{

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainNavHostFragment: MainNavHostFragment)

    fun inject(listFragment: ListFragment)

    fun inject(detailFragment: DetailFragment)

    fun inject(finalFragment: FinalFragment)

}













