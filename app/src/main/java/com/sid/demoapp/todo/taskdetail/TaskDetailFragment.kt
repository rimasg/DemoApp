package com.sid.demoapp.todo.taskdetail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.*
import android.widget.CheckBox
import com.sid.demoapp.R
import com.sid.demoapp.databinding.FragmentTaskDetailBinding
import com.sid.demoapp.todo.util.setupSnackbar

class TaskDetailFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentTaskDetailBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        viewDataBinding.viewmodel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }
    }

    private fun setupFab() {
        activity!!.findViewById<View>(R.id.fab_edit_task).setOnClickListener {
            viewDataBinding.viewmodel?.editTask()
        }
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.start(arguments!!.getString(ARGUMENT_TASK_ID))
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
        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                viewDataBinding.viewmodel?.deleteTask()
                return true
            }
            else -> return false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_taskdetail_fragment, menu)
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