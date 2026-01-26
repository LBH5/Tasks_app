package com.brnaime.tasksapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brnaime.tasksapp.data.models.Task

@Database(entities = [Task::class], version = 2, exportSchema = false)
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
                    "tasks_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}