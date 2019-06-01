package com.sid.demoapp.todo.data.mock

import android.content.Context
import com.sid.demoapp.todo.data.source.TasksRepository
import com.sid.demoapp.todo.data.source.local.TasksLocalDataSource
import com.sid.demoapp.todo.data.source.local.ToDoDatabase
import com.sid.demoapp.todo.data.source.remote.TasksRemoteDataSource
import com.sid.demoapp.todo.util.AppExecutors

object Injection {

    fun provideTasksRepository(context: Context): TasksRepository {
        val database = ToDoDatabase.getInstance(context)
        return TasksRepository.getInstance(
                TasksRemoteDataSource,
                TasksLocalDataSource.getInstance(AppExecutors(), database.tasksDao()))
    }
}