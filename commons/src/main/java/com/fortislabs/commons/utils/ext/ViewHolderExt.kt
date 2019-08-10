package com.fortislabs.commons.utils.ext

import android.view.View
import androidx.annotation.IdRes

fun <T : View> bindView(container: View, @IdRes res: Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { container.findViewById<T>(res) }
}

fun <T : View> bindOptionalView(container: View?, @IdRes res: Int) : Lazy<T?> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { container?.findViewById<T>(res) }
}
