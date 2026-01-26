package com.brnaime.tasksapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brnaime.tasksapp.TasksApplication
import com.brnaime.tasksapp.data.models.Task
import kotlinx.coroutines.launch


class MainViewModel: ViewModel() {

    private val taskRepository = TasksApplication.taskRepository


    fun createTask(title:String,description:String?){

        val task = Task(
            title = title,
            description = description
        )
        viewModelScope.launch {
            taskRepository.createTask(task)
        }

    }

}