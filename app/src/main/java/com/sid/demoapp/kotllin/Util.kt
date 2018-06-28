package com.sid.demoapp.kotllin

/**
 * Created by rgaina on 28/06/2018.
 */

inline fun <T> (() -> T).invokeTest(): T {
    print("Running test")
    return this.invoke()
}
