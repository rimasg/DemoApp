package com.fortislabs.commons.utils.ext

import android.support.annotation.IdRes
import android.view.View

fun <T : View> View.bindView(@IdRes res: Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}