package com.brnaime.tasksapp.data

import com.brnaime.tasksapp.data.database.TaskDAO
import com.brnaime.tasksapp.data.models.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDAO) {

    suspend fun createTask(task: Task) {
        taskDao.createTask(task)
    }

    suspend fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }
    fun getStarredTasks(): Flow<List<Task>> {
        return taskDao.getStarredTasks()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }



}