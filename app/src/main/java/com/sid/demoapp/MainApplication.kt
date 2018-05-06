package com.sid.demoapp

import android.app.Application
import com.sid.demoapp.di.simpleModule
import org.koin.android.ext.android.startKoin

/**
 * Created by rgaina on 06/05/2018.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(simpleModule))
    }
}