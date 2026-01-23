package com.brnaime.tasksapp.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brnaime.tasksapp.data.models.Task
import com.brnaime.tasksapp.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TasksAdapter(private val listener: TaskUpdatedListener): RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

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
            binding.textViewTaskTitle.text = task.title
            binding.textViewTaskDescription.text = task.description
            binding.checkboxComplete.isChecked = task.isCompleted
            binding.checkboxToggleStar.isChecked = task.isStarred

            if (task.isCompleted){
                binding.textViewTaskTitle.paintFlags = binding.textViewTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewTaskDescription.paintFlags = binding.textViewTaskDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewTaskTitle.paintFlags = 0
                binding.textViewTaskDescription.paintFlags = 0
            }

            binding.checkboxComplete.setOnClickListener {
                val updatedTask = task.copy(isCompleted = (it as MaterialCheckBox).isChecked)
                listener.onTaskUpdated(updatedTask)
            }
            binding.checkboxToggleStar.setOnClickListener {
                val updatedTask = task.copy(isStarred = (it as MaterialCheckBox).isChecked)
                listener.onTaskUpdated(updatedTask)
            }

        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}


