package com.sid.demoapp

import com.sid.demoapp.di.DaggerAppComponent
import com.sid.demoapp.di.simpleModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.koin.android.ext.android.startKoin

/**
 * Created by rgaina on 06/05/2018.
 */
class MainApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(simpleModule))
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}