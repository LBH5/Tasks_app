package com.brnaime.tasksapp.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.brnaime.tasksapp.data.models.Task
import com.brnaime.tasksapp.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TasksAdapter(private val listener: TaskListener): RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private var tasks: List<Task> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks.sortedBy { it.isCompleted }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        return holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


    inner class TaskViewHolder(private val binding: ItemTaskBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {

                root.setOnLongClickListener {
                    listener.onTaskDeleted(task)
                    true
                }
                textViewTaskTitle.text = task.title
                checkboxComplete.isChecked = task.isCompleted
                checkboxToggleStar.isChecked = task.isStarred


                if (task.description.isNullOrEmpty())
                    textViewTaskDescription.visibility = View.GONE
                else {
                    textViewTaskDescription.text = task.description
                    textViewTaskDescription.visibility = View.VISIBLE
                }

                if (task.isCompleted){
                    textViewTaskTitle.paintFlags = textViewTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textViewTaskDescription.paintFlags = textViewTaskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTaskTitle.paintFlags = 0
                    textViewTaskDescription.paintFlags = 0
                }

                checkboxComplete.setOnClickListener {
                    val updatedTask = task.copy(isCompleted = (it as MaterialCheckBox).isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
                checkboxToggleStar.setOnClickListener {
                    val updatedTask = task.copy(isStarred = (it as MaterialCheckBox).isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
            }

        }
    }

    interface TaskListener {
        fun onTaskUpdated(task: Task)
        fun onTaskDeleted(task: Task)
    }
}


