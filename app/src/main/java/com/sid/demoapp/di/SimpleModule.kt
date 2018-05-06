package com.sid.demoapp.di

import com.sid.demoapp.kotllin.DummyDataSet
import com.sid.demoapp.model.SimpleViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
 * Created by rgaina on 06/05/2018.
 */

val simpleModule = applicationContext {

    viewModel { SimpleViewModel(get()) }

    bean { DummyDataSet }
}