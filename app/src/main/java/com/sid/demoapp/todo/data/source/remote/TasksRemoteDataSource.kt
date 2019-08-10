package com.sid.demoapp.todo.data.source.remote

import android.os.Handler
import com.google.common.collect.Lists
import com.sid.demoapp.todo.data.source.TasksDataSource
import com.sid.demoapp.todo.data.source.local.Task

object TasksRemoteDataSource : TasksDataSource {
    private const val SERVICE_LATENCY_IN_MILLIS = 2000L
    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Task>(2)

    init {
        addTask("Refactor code", "Remove Mono from the source code")
        addTask("Project party", "Arrange a party to celebrate the project success")
    }

    private fun addTask(title: String, descripion: String) {
        val newTask = Task(title, descripion)
        TASKS_SERVICE_DATA.put(newTask.id, newTask)
    }

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        val tasks = Lists.newArrayList(TASKS_SERVICE_DATA.values)
        Handler().postDelayed({
            callback.onTasksLoaded(tasks)
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val task = TASKS_SERVICE_DATA[taskId]
        with(Handler()) {
            if (task != null) {
                postDelayed({ callback.onTaskLoaded(task) }, SERVICE_LATENCY_IN_MILLIS)
            } else {
                postDelayed({ callback.onDataNotAvailable() }, SERVICE_LATENCY_IN_MILLIS)
            }
        }
    }

    override fun saveTask(task: Task) {
        TASKS_SERVICE_DATA.put(task.id, task)
    }

    override fun completeTask(task: Task) {
        val completedTask = Task(task.title, task.description, task.id).apply {
            isCompleted = true
        }
        TASKS_SERVICE_DATA.put(task.id, completedTask)
    }

    override fun completeTask(taskId: String) {
        // Not required because {@link TasksRepository} handles converting
    }

    override fun activateTask(task: Task) {
        val activeTask = Task(task.title, task.description, task.id)
        TASKS_SERVICE_DATA.put(task.id, activeTask)
    }

    override fun activateTask(taskId: String) {
        // Not required because {@link TasksRepository} handles converting
    }

    override fun clearCompletedTasks() {
        TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filterValues { !it.isCompleted } as LinkedHashMap<String, Task>
    }

    override fun refreshTasks() {
        // Not required because {@link TasksRepository} handles converting
    }

    override fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }
}