package com.sid.demoapp.todo.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        private var INSTANCE: ToDoDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ToDoDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.applicationContext,
                                    ToDoDatabase::class.java,
                                    "Tasks.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}
