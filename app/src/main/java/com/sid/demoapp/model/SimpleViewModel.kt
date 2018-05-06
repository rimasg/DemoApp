package com.sid.demoapp.model

import android.arch.lifecycle.ViewModel
import com.sid.demoapp.kotllin.DummyDataSet

/**
 * Created by rgaina on 06/05/2018.
 */
class SimpleViewModel(private val dummyDataSet : DummyDataSet) : ViewModel() {

    val dummyData
        get() = dummyDataSet.dataSet
}