package com.sid.demoapp.todo.tasks

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import com.sid.demoapp.R
import com.sid.demoapp.todo.SingleLiveEvent
import com.sid.demoapp.todo.addedittask.AddEditTaskActivity
import com.sid.demoapp.todo.data.source.TasksDataSource
import com.sid.demoapp.todo.data.source.TasksRepository
import com.sid.demoapp.todo.data.source.local.Task
import com.sid.demoapp.todo.util.ADD_EDIT_RESULT_OK
import com.sid.demoapp.todo.util.DELETE_RESULT_OK
import com.sid.demoapp.todo.util.EDIT_RESULT_OK

class TasksViewModel(
        context: Application,
        private val tasksRepository: TasksRepository
) : AndroidViewModel(context) {

    private val isDataLoadingError = ObservableBoolean(false)
    private val context: Context = context.applicationContext

    internal val openTaskEvent = SingleLiveEvent<String>()

    val items: ObservableList<Task> = ObservableArrayList()
    val dataLoading = ObservableBoolean(false)
    val currentFilteringLabel = ObservableField<String>()
    val noTasksLabel = ObservableField<String>()
    val noTaskIconRes = ObservableField<Drawable>()
    val empty = ObservableBoolean(false)
    val tasksAddViewVisible = ObservableBoolean()
    val snackbarMessage = SingleLiveEvent<Int>()
    val newTaskEvent = SingleLiveEvent<Void>()

    var currentFiltering = TasksFilterType.ALL_TASKS
        set(value) {
            field = value
            updateFiltering()
        }

    fun start() {
        loadTasks(false)
    }

    fun loadTasks(forceUpdate: Boolean) {
        loadTasks(forceUpdate, true)
    }

    fun updateFiltering() {
        when (currentFiltering) {
            TasksFilterType.ALL_TASKS ->
                setFilter(R.string.label_all, R.string.no_tasks_all, R.drawable.ic_assignment_turned_in_24dp, false)
            TasksFilterType.ACTIVE_TASKS ->
                setFilter(R.string.label_active, R.string.no_tasks_active, R.drawable.ic_check_circle_24dp, false)
            TasksFilterType.COMPLETED_TASKS ->
                setFilter(R.string.label_completed, R.string.no_tasks_completed, R.drawable.ic_verified_user_24dp,false)
        }
    }

    private fun setFilter(@StringRes filteringLabelString: Int, @StringRes noTasksLabelingString: Int,
                  @DrawableRes noTasksIconDrawable: Int, tasksAddVisible: Boolean) {
        with(context.resources) {
            currentFilteringLabel.set(getString(filteringLabelString))
            noTasksLabel.set(getString(noTasksLabelingString))
            noTaskIconRes.set(getDrawable(noTasksIconDrawable))
            tasksAddViewVisible.set(tasksAddVisible)
        }
    }

    fun clearCompleted() {
        tasksRepository.clearCompletedTasks()
        snackbarMessage.value = R.string.completed_tasks_cleared
        loadTasks(false, false)
    }

    fun completeTask(task: Task, completed: Boolean) {
        task.isCompleted = completed

        if (completed) {
            tasksRepository.completeTask(task)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            tasksRepository.activateTask(task)
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.value = message
    }

    fun addNewTask() {
        newTaskEvent.call()
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int) {
        if (AddEditTaskActivity.REQUEST_CODE == requestCode) {
            snackbarMessage.value =
                    when (resultCode) {
                        EDIT_RESULT_OK ->
                            R.string.successfully_saved_task_message
                        ADD_EDIT_RESULT_OK ->
                            R.string.successfully_added_task_message
                        DELETE_RESULT_OK ->
                            R.string.successfully_deleted_task_message
                        else -> return
                    }
        }
    }

    private fun loadTasks(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            dataLoading.set(true)
        }

        if (forceUpdate) {
            tasksRepository.refreshTasks()
        }

        tasksRepository.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                val tasksToShow: List<Task>

                when (currentFiltering) {
                    TasksFilterType.ALL_TASKS ->
                        tasksToShow = tasks
                    TasksFilterType.ACTIVE_TASKS ->
                        tasksToShow = tasks.filter { it.isActive }
                    TasksFilterType.COMPLETED_TASKS ->
                        tasksToShow = tasks.filter { it.isCompleted }
                }

                if (showLoadingUI) {
                    dataLoading.set(false)
                }
                isDataLoadingError.set(false)

                with(items) {
                    clear()
                    addAll(tasksToShow)
                    empty.set(isEmpty())
                }
            }

            override fun onDataNotAvailable() {
                isDataLoadingError.set(true)
            }
        })
    }
}