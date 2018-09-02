package com.sid.demoapp

import android.arch.lifecycle.ViewModel
import com.sid.demoapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class KotlinModule {

    @Binds
    @IntoMap
    @ViewModelKey(KotlinViewModel::class)
    internal abstract fun bindKotlinViewModel(viewModel: KotlinViewModel): ViewModel
}