package com.sid.demoapp.kotllin

/**
 * Created by rgaina on 10/03/2018.
 */
object DummyDataSet {

    val dataSet: ArrayList<String> by lazy {
        val data = ArrayList<String>()
        for (i in 0..20) {

            data.add("Item #$i")
        }
        data
    }
}
