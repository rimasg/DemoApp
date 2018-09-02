package com.sid.demoapp

import android.arch.lifecycle.ViewModel
import javax.inject.Inject

class KotlinViewModel @Inject constructor() : ViewModel() {

    val modelName = "Name from ${KotlinModule::class.java.simpleName} class"
}