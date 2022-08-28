package com.sid.demoapp.todo.tasks

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sid.demoapp.R
import com.sid.demoapp.todo.addedittask.AddEditTaskActivity
import com.sid.demoapp.todo.taskdetail.TaskDetailActivity
import com.sid.demoapp.todo.util.obtainViewModel
import com.sid.demoapp.todo.util.replaceFragmentInActivity
import com.sid.demoapp.todo.util.setupActionBar
import timber.log.Timber

class TasksActivity : AppCompatActivity(), TaskItemNavigator, TasksNavigator {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewModel: TasksViewModel
    private val taskDetailActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Timber.d("Task Details displayed")
        viewModel.handleActivityResult(result.resultCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        setupNavigationDrawer()
        setupViewFragment()

        viewModel = obtainViewModel().apply {
            openTaskEvent.observe(this@TasksActivity) { taskId ->
                if (taskId != null) {
                    this@TasksActivity.openTaskDetails(taskId)
                }
            }
            newTaskEvent.observe(this@TasksActivity) {
                this@TasksActivity.addNewTask()
            }
        }
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
        TasksFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.list_navigation_menu_item -> {
                    // Do nothing, we're already on that screen
                }
                R.id.statistics_navigation_menu_item -> {
                    TODO("Implement method")
/*
                    val intent = Intent(this@TasksActivity, StatisticsActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    startActivity(null)
*/
                }
            }
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun openTaskDetails(taskId: String) {
        val intent = Intent(this, TaskDetailActivity::class.java).apply {
            putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId)
        }
        taskDetailActivityLauncher.launch(intent)
    }

    override fun addNewTask() {
        val intent = Intent(this, AddEditTaskActivity::class.java)
        startActivity(intent)
    }

    fun obtainViewModel(): TasksViewModel = obtainViewModel(TasksViewModel::class.java)
}
