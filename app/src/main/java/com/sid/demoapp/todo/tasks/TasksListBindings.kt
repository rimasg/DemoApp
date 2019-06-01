package com.sid.demoapp.todo.tasks

import android.databinding.BindingAdapter
import android.widget.ListView
import com.sid.demoapp.todo.data.source.local.Task

object TasksListBindings {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: ListView, items: List<Task>) {
        with(listView.adapter as TasksAdapter) {
            replaceData(items)
        }
    }
}