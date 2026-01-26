package com.brnaime.tasksapp.data

import com.brnaime.tasksapp.data.database.TaskDAO
import com.brnaime.tasksapp.data.models.Task

class TaskRepository(private val taskDao: TaskDAO) {

    suspend fun createTask(task: Task) {
        taskDao.createTask(task)
    }

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

}