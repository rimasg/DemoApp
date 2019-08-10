package com.sid.demoapp.todo.addedittask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sid.demoapp.R
import com.sid.demoapp.todo.util.ADD_EDIT_RESULT_OK
import com.sid.demoapp.todo.util.obtainViewModel
import com.sid.demoapp.todo.util.replaceFragmentInActivity
import com.sid.demoapp.todo.util.setupActionBar

class AddEditTaskActivity : AppCompatActivity(), AddEditTaskNavigator {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onTaskSaved() {
        setResult(ADD_EDIT_RESULT_OK)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        replaceFragmentInActivity(obtainViewFragment(), R.id.contentFrame)

        subscribeToNavigationChanges()
    }


    private fun subscribeToNavigationChanges() {
        obtainViewModel().taskUpdatedEvent.observe(this, Observer {
            this@AddEditTaskActivity.onTaskSaved()
        })
    }

    private fun obtainViewFragment() = supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
            AddEditTaskFragment.newInstance().apply {
                arguments = Bundle().apply {
                    putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID,
                            intent.getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID))
                }
            }

    fun obtainViewModel(): AddEditTaskViewModel = obtainViewModel(AddEditTaskViewModel::class.java)

    companion object {
        const val REQUEST_CODE = 1
    }
}
