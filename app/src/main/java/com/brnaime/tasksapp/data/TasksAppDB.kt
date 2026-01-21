package com.brnaime.tasksapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brnaime.tasksapp.data.dao.TaskDAO
import com.brnaime.tasksapp.data.models.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TasksAppDB: RoomDatabase() {
    abstract fun getTaskDAO(): TaskDAO

    companion object {

        @Volatile
        private var DATABASE_INSTANCE: TasksAppDB? = null

        fun getDatabase(context: Context): TasksAppDB {

            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TasksAppDB::class.java,
                    "tasks_database")
                    .build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}