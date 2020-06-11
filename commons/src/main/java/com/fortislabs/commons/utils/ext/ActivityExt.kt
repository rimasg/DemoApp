package com.fortislabs.commons.utils.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> androidx.fragment.app.FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) = ViewModelProvider(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> androidx.fragment.app.Fragment.viewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProvider(this, provider).get(VM::class.java)
