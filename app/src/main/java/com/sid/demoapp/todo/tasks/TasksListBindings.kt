package com.sid.demoapp.todo.tasks

import android.widget.ListView
import androidx.databinding.BindingAdapter
import com.sid.demoapp.todo.data.source.local.Task

object TasksListBindings {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: ListView, items: List<Task>) {
        with(listView.adapter as TasksAdapter) {
            replaceData(items)
        }
    }
}