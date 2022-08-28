package com.sid.demoapp.kotllin

/**
 * Created by rgaina on 28/06/2018.
 */
class DummyClass {
    fun helloWorld(name: String) {
        print("Hello, $name!")
    }

    fun testMethod(method: () -> Unit) {
        method.invokeTest()
    }

}

fun main() {
    val dummyClass = DummyClass()
    dummyClass.testMethod { dummyClass.helloWorld("John") }
}