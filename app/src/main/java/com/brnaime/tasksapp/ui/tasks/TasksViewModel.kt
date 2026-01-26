package com.brnaime.tasksapp.ui.tasks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brnaime.tasksapp.TasksApplication
import com.brnaime.tasksapp.data.models.Task
import kotlinx.coroutines.launch

class TasksViewModel: ViewModel() {


    private val taskRepository = TasksApplication.taskRepository


    suspend fun fetchTasks(): List<Task> {
        val tasks = taskRepository.getAllTasks()
        return tasks
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

}