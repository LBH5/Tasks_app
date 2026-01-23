package com.brnaime.tasksapp.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brnaime.tasksapp.data.TasksAppDB
import com.brnaime.tasksapp.data.models.Task
import com.brnaime.tasksapp.databinding.FragmentTasksBinding
import kotlin.concurrent.thread
import kotlin.getValue

class TasksFragment : Fragment(), TasksAdapter.TaskUpdatedListener {

    private lateinit var binding: FragmentTasksBinding
    private val tasksDao by lazy {
            TasksAppDB.getDatabase(requireContext()).getTaskDAO()
    }
    private val tasksAdapter: TasksAdapter by lazy {
        TasksAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewTasks.adapter = tasksAdapter
        fetchAllTasks()
    }

    public fun fetchAllTasks() {
        thread {
            val tasks = tasksDao.getAllTasks()
            requireActivity().runOnUiThread {
                tasksAdapter.setTasks(tasks)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        thread {
            tasksDao.updateTask(task)
            requireActivity().runOnUiThread {
                fetchAllTasks()
            }
        }

    }

}