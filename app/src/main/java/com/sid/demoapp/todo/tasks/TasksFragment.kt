package com.sid.demoapp.todo.tasks

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.R
import com.sid.demoapp.databinding.FragmentTasksBinding
import com.sid.demoapp.todo.util.setupSnackbar

class TasksFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentTasksBinding
    private lateinit var listAdapter: TasksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentTasksBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as TasksActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_tasks_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.menu_clear -> {
                    viewDataBinding.viewmodel?.clearCompleted()
                    true
                }
                R.id.menu_filter -> {
                    showFilteringPopUpMenu()
                    true
                }
                R.id.menu_refresh -> {
                    viewDataBinding.viewmodel?.loadTasks(true)
                    true
                }
                else -> false
            }

    private fun showFilteringPopUpMenu() {
        PopupMenu(context!!, activity!!.findViewById<View>(R.id.menu_filter)).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            setOnMenuItemClickListener {
                viewDataBinding.viewmodel?.run {
                    currentFiltering =
                            when (it.itemId) {
                                R.id.active -> TasksFilterType.ACTIVE_TASKS
                                R.id.completed -> TasksFilterType.COMPLETED_TASKS
                                else -> TasksFilterType.ALL_TASKS
                            }
                    loadTasks(false)
                }
                true
            }
            show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewmodel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }
        setupFab()
        setupListAdapter()
        setupRefreshLayout()
    }

    private fun setupFab() {
        activity!!.findViewById<FloatingActionButton>(R.id.fab_add_task).run {
            setImageResource(R.drawable.ic_add)
            setOnClickListener {
                viewDataBinding.viewmodel?.addNewTask()
            }
        }
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = TasksAdapter(ArrayList(0), viewModel)
            viewDataBinding.tasksList.adapter = listAdapter
        } else {
            Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupRefreshLayout() {
        viewDataBinding.refreshLayout.run {
            setColorSchemeColors(
                    ContextCompat.getColor(activity!!, R.color.colorPrimary),
                    ContextCompat.getColor(activity!!, R.color.colorAccent),
                    ContextCompat.getColor(activity!!, R.color.colorPrimaryDark)
            )
            scrollUpChild = viewDataBinding.tasksList
        }
    }

    companion object {
        fun newInstance() = TasksFragment()
        private const val TAG = "TasksFragment"
    }
}