package com.sid.demoapp.todo.addedittask

import android.app.Application
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.sid.demoapp.R
import com.sid.demoapp.todo.SingleLiveEvent
import com.sid.demoapp.todo.data.source.TasksDataSource
import com.sid.demoapp.todo.data.source.TasksRepository
import com.sid.demoapp.todo.data.source.local.Task

class AddEditTaskViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context), TasksDataSource.GetTaskCallback {

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val dataLoading = ObservableBoolean(false)
    internal val snackbarMessage = SingleLiveEvent<Int>()
    internal val taskUpdatedEvent = SingleLiveEvent<Void>()
    private var taskId: String? = null
    private val isNewTask
        get() = taskId == null
    private var isDataLoaded = false
    private var taskCompleted = false

    fun start(taskId: String?) {
        if (dataLoading.get()) {
            return
        }
        this.taskId = taskId
        if (isNewTask || isDataLoaded) {
            return
        }
        dataLoading.set(true)
        taskId?.let {
            tasksRepository.getTask(it, this)
        }
    }

    override fun onTaskLoaded(task: Task) {
        title.set(task.title)
        description.set(task.description)
        taskCompleted = task.isCompleted
        dataLoading.set(false)
        isDataLoaded = true
    }

    override fun onDataNotAvailable() {
        dataLoading.set(false)
    }

    fun saveTask() {
        val task = Task(title.get()!!, description.get()!!)
        if (task.isEmpty) {
            showSnackBarMessage(R.string.empty_task_message)
            return
        }
        if (isNewTask) {
            createTask(task)
        } else {
            taskId?.let {
                updateTask(Task(title.get()!!, description.get()!!, it)
                        .apply { isCompleted = taskCompleted })
            }
        }
    }

    private fun createTask(newTask: Task) {
        tasksRepository.saveTask(newTask)
        taskUpdatedEvent.call()
    }

    private fun updateTask(task:Task) {
        if (isNewTask) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        tasksRepository.saveTask(task)
        taskUpdatedEvent.call()
    }

    private fun showSnackBarMessage(@StringRes message: Int) {
        snackbarMessage.value = message
    }
}