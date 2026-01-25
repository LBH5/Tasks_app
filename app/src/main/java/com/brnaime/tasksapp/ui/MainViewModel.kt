package com.brnaime.tasksapp.ui

import androidx.lifecycle.ViewModel
import com.brnaime.tasksapp.data.TasksAppDB
import com.brnaime.tasksapp.data.models.Task
import kotlin.concurrent.thread

class MainViewModel: ViewModel() {

    private val database by lazy { TasksAppDB.getDatabase(MainActivity()) }
    private val taskDao by lazy { database.getTaskDAO() }

    fun createTask(title:String,description:String?){

        val task = Task(
            title = title,
            description = description
        )
        thread {
            taskDao.createTask(task)
        }

    }

}