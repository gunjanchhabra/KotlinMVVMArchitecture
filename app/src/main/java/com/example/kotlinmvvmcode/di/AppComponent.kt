package com.example.kotlinmvvmcode.di

import android.content.Context
import com.example.kotlinmvvmcode.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent{

    fun inject(mainActivity: MainActivity)

   @Component.Factory
    interface FactoryInstance{
        fun createInstance(@BindsInstance context: Context) : AppComponent
    }
}