package com.brnaime.tasksapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.brnaime.tasksapp.data.models.Task

@Dao
interface TaskDAO {

    @Insert
    fun createTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)


}