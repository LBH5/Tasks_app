package com.brnaime.tasksapp

import android.app.Application
import com.brnaime.tasksapp.data.TaskRepository
import com.brnaime.tasksapp.data.database.TasksAppDB

class TasksApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val database = TasksAppDB.getDatabase(this)
        val taskDao = database.getTaskDAO()
        taskRepository = TaskRepository(taskDao)
    }

    companion object {
        lateinit var taskRepository: TaskRepository


    }
}