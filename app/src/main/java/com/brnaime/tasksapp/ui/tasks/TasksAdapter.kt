package com.brnaime.tasksapp.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brnaime.tasksapp.data.models.Task
import com.brnaime.tasksapp.databinding.ItemTaskBinding

class TasksAdapter(private val tasks: List<Task>): RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

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


    class TaskViewHolder(private val binding: ItemTaskBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textViewTaskTitle.text = task.title
            binding.textViewTaskDescription.text = task.description
        }
    }
}