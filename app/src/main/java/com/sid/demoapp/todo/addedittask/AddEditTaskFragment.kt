package com.sid.demoapp.todo.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.R
import com.sid.demoapp.databinding.FragmentAddTaskBinding
import com.sid.demoapp.todo.util.setupSnackbar

class AddEditTaskFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentAddTaskBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFab()
        viewDataBinding.viewmodel?.let {
            view.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }
        setupActionBar()
        loadData()
    }

    private fun loadData() {
        viewDataBinding.viewmodel?.start(arguments?.getString(ARGUMENT_EDIT_TASK_ID))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_add_task, container, false)
        viewDataBinding = FragmentAddTaskBinding.bind(root).apply {
            viewmodel = (activity as AddEditTaskActivity).obtainViewModel()
        }
        setHasOptionsMenu(true)
        return  viewDataBinding.root
    }

    private fun setupFab() {
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_edit_task_done).apply {
            setImageResource(R.drawable.ic_done)
            setOnClickListener { viewDataBinding.viewmodel?.saveTask() }
        }
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).supportActionBar?.setTitle(
                if (arguments != null && arguments?.get(ARGUMENT_EDIT_TASK_ID) != null)
                    R.string.edit_task
                else
                    R.string.add_task
        )
    }

    companion object {
        const val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"

        fun newInstance() = AddEditTaskFragment()
    }
}