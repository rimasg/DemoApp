package com.sid.demoapp.di

import com.sid.demoapp.KotlinActivity
import com.sid.demoapp.KotlinModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [KotlinModule::class])
    internal abstract fun kotlinActivity(): KotlinActivity
}