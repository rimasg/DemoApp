package com.fortislabs.commons.utils.ext

import android.view.View
import androidx.annotation.IdRes

fun <T : View> View.bindView(@IdRes res: Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}