package com.sid.demoapp.todo

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sid.demoapp.todo.addedittask.AddEditTaskViewModel
import com.sid.demoapp.todo.data.mock.Injection
import com.sid.demoapp.todo.data.source.TasksRepository
import com.sid.demoapp.todo.taskdetail.TaskDetailViewModel
import com.sid.demoapp.todo.tasks.TasksViewModel

class ViewModelFactory private constructor(
        private val application: Application,
        private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(TaskDetailViewModel::class.java) ->
                        TaskDetailViewModel(application, tasksRepository)
                    isAssignableFrom(AddEditTaskViewModel::class.java) ->
                        AddEditTaskViewModel(application, tasksRepository)
                    isAssignableFrom(TasksViewModel::class.java) ->
                        TasksViewModel(application, tasksRepository)
                    else -> throw IllegalArgumentException("Unknown ViewModelClass: ${modelClass.name}")
                }
            } as T

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(application,
                            Injection.provideTasksRepository(application.applicationContext))
                            .also { INSTANCE = it }
                }
    }
}