package com.sid.demoapp.corountines.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

fun <T: ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T): (A) -> ViewModelProvider.Factory {
    return {arg: A ->
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel?> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}