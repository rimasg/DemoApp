package com.sid.demoapp.todo.taskdetail

import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.R
import com.sid.demoapp.databinding.FragmentTaskDetailBinding
import com.sid.demoapp.todo.util.setupSnackbar

class TaskDetailFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentTaskDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFab()
        viewDataBinding.viewmodel?.let {
            view.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }

        initMenu()
    }

    private fun setupFab() {
        requireActivity().findViewById<View>(R.id.fab_edit_task).setOnClickListener {
            viewDataBinding.viewmodel?.editTask()
        }
    }
    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_taskdetail_fragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_delete -> {
                        viewDataBinding.viewmodel?.deleteTask()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onResume() {
        super.onResume()
        val taskId = arguments?.getString(ARGUMENT_TASK_ID)
        if (taskId != null) {
            viewDataBinding.viewmodel?.start(taskId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)
        viewDataBinding = FragmentTaskDetailBinding.bind(view).apply {
            viewmodel = (activity as TaskDetailActivity).obtainViewModel()
            listener = object : TaskDetailUserActionsListener {
                override fun onCompleteChanged(v: View) {
                    viewmodel?.setCompleted((v as CheckBox).isChecked)
                }
            }
        }
        return view
    }

    companion object {
        const val ARGUMENT_TASK_ID = "TASK_ID"
        const val REQUEST_EDIT_TASK = 1

        fun newInstance(taskId: String) = TaskDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_TASK_ID, taskId)
            }
        }
    }
}
