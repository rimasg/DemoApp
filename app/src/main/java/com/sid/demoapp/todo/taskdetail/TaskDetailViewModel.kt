package com.sid.demoapp.todo.taskdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.annotation.StringRes
import com.sid.demoapp.R
import com.sid.demoapp.todo.SingleLiveEvent
import com.sid.demoapp.todo.data.source.TasksDataSource
import com.sid.demoapp.todo.data.source.TasksRepository
import com.sid.demoapp.todo.data.source.local.Task

class TaskDetailViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context), TasksDataSource.GetTaskCallback {

    val task = ObservableField<Task>()
    val completed = ObservableBoolean()
    val editTaskCommand = SingleLiveEvent<Void>()
    val deleteTaskCommand = SingleLiveEvent<Void>()
    val snackbarMessage = SingleLiveEvent<Int>()
    var isDataLoading = false
        private set
    val isDataAvailable
        get() = task.get() != null

    fun deleteTask() {
        task.get()?.let {
            tasksRepository.deleteTask(it.id)
            deleteTaskCommand.call()
        }
    }

    fun editTask() {
        editTaskCommand.call()
    }

    fun setCompleted(completed: Boolean) {
        if (isDataLoading) {
            return
        }
        val task = this.task.get()?.apply {
            isCompleted = completed
        }!!
        if (completed) {
            tasksRepository.completeTask(task)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            tasksRepository.activateTask(task)
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    fun start(taskId: String) {
        taskId.let {
            isDataLoading = true
            tasksRepository.getTask(it, this)
        }
    }

    fun setTask(task: Task) {
        this.task.set(task)
        completed.set(task.isCompleted)
    }

    override fun onTaskLoaded(task: Task) {
        setTask(task)
        isDataLoading =false
    }

    override fun onDataNotAvailable() {
        task.set(null)
        isDataLoading = false
    }

    fun onRefresh() {
        if (task.get() != null) {
            start(task.get()!!.id)
        }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        snackbarMessage.value = message
    }
}