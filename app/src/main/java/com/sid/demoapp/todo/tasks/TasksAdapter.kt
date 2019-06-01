package com.sid.demoapp.todo.tasks

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import com.sid.demoapp.databinding.TaskItemBinding
import com.sid.demoapp.todo.data.source.local.Task

class TasksAdapter(
        private var tasks: List<Task>,
        private var tasksViewModel: TasksViewModel
) : BaseAdapter() {

    fun replaceData(tasks: List<Task>) {
        setList(tasks)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: TaskItemBinding
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            binding = TaskItemBinding.inflate(inflater, parent, false)
        } else {
            binding = DataBindingUtil.getBinding<TaskItemBinding>(convertView)!!
        }

        val userActionsListener = object : TaskItemUserActionsListener {
            override fun onCompleteChanged(task: Task, v: View) {
                val checked = (v as CheckBox).isChecked
                tasksViewModel.completeTask(task, checked)
            }

            override fun onTaskClicked(task: Task) {
                tasksViewModel.openTaskEvent.value = task.id
            }
        }

        with(binding) {
            task = tasks[position]
            listener = userActionsListener
            executePendingBindings()
        }

        return binding.root
    }

    override fun getItem(position: Int): Any = tasks[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = tasks.size

    private fun setList(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}