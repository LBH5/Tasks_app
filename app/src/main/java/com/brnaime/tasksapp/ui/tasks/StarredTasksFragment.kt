package com.brnaime.tasksapp.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.brnaime.tasksapp.data.models.Task
import com.brnaime.tasksapp.databinding.FragmentTasksBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class StarredTasksFragment: Fragment(), TasksAdapter.TaskListener {


    private val viewModel by viewModels<StarredTasksViewModel>()
    private lateinit var binding: FragmentTasksBinding
    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewTasks.adapter = tasksAdapter
        fetchTasks()
    }

    private fun fetchTasks(){
        lifecycleScope.launch {
            val tasks: Flow<List<Task>> = viewModel.fetchTasks()
            tasks.collectLatest { tasks ->
                tasksAdapter.setTasks(tasks)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        viewModel.updateTask(task)
    }

    override fun onTaskDeleted(task: Task) {
        viewModel.deleteTask(task)
    }
}