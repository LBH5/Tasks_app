package com.brnaime.tasksapp.ui.tasks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brnaime.tasksapp.TasksApplication
import com.brnaime.tasksapp.data.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StarredTasksViewModel: ViewModel() {


    private val taskRepository = TasksApplication.taskRepository


    fun fetchTasks(): Flow<List<Task>> {
        val tasks = taskRepository.getStarredTasks()
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