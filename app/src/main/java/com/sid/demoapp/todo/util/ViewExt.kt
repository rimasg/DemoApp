package com.sid.demoapp.todo.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.support.design.widget.Snackbar
import android.view.View
import com.sid.demoapp.todo.ScrollChildSwipeRefreshLayout
import com.sid.demoapp.todo.SingleLiveEvent
import com.sid.demoapp.todo.tasks.TasksViewModel

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

fun View.setupSnackbar(lifecycleOwner: LifecycleOwner, snackbarMessageLiveEvent: SingleLiveEvent<Int>, timeLength: Int) {
    snackbarMessageLiveEvent.observe(lifecycleOwner, Observer {
        it?.let { showSnackbar(context.getString(it), timeLength) }
    })
}

@BindingAdapter("android:onRefresh")
fun ScrollChildSwipeRefreshLayout.setSwipeRefreshLayoutOnRefreshListener(viewModel: TasksViewModel) {
    setOnRefreshListener { viewModel.loadTasks(true) }
}