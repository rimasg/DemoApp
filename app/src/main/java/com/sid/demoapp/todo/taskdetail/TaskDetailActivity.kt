package com.sid.demoapp.todo.taskdetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sid.demoapp.R
import com.sid.demoapp.todo.addedittask.AddEditTaskActivity
import com.sid.demoapp.todo.addedittask.AddEditTaskFragment
import com.sid.demoapp.todo.taskdetail.TaskDetailFragment.Companion.REQUEST_EDIT_TASK
import com.sid.demoapp.todo.util.*

class TaskDetailActivity : AppCompatActivity(), TaskDetailNavigator {

    private lateinit var taskViewModel: TaskDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_task_detail)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        replaceFragmentInActivity(findOrCreateViewFragment(), R.id.contentFrame)

        taskViewModel = obtainViewModel()

        subscribeToNavigationChanges(taskViewModel)
    }

    private fun findOrCreateViewFragment() = supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
            TaskDetailFragment.newInstance(intent.getStringExtra(EXTRA_TASK_ID))

    private fun subscribeToNavigationChanges(viewModel: TaskDetailViewModel) {
        val activity = this@TaskDetailActivity
        viewModel.run {
            editTaskCommand.observe(activity, Observer {
                activity.onStartEditTask()
            })
            deleteTaskCommand.observe(activity, Observer {
                activity.onTaskDeleted()
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_EDIT_TASK) {
            if (resultCode == ADD_EDIT_RESULT_OK) {
                setResult(EDIT_RESULT_OK)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onTaskDeleted() {
        setResult(DELETE_RESULT_OK)
        finish()
    }

    override fun onStartEditTask() {
        val taskId = intent.getStringExtra(EXTRA_TASK_ID)
        val intent = Intent(this, AddEditTaskActivity::class.java).apply {
            putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId)
        }
        startActivityForResult(intent, REQUEST_EDIT_TASK)
    }

    fun obtainViewModel(): TaskDetailViewModel = obtainViewModel(TaskDetailViewModel::class.java)

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }
}