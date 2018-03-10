package com.sid.demoapp.kotllin

/**
 * Created by rgaina on 10/03/2018.
 */
object DummyDataSet {

    private val data = ArrayList<String>()

    val dataSet: ArrayList<String> by lazy {
        initDataSet()
        data
    }

    private fun initDataSet() {
        for (i in 0..20) {
            data.add("Item #$i")
        }
    }
}