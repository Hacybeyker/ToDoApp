package com.hacybeyker.todoapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hacybeyker.todoapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class)
abstract class ToDoDataBase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDataBase? = null

        fun getDatabase(context: Context): ToDoDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                Log.d("TAG", "Here - getDatabase: in IF")
                return tempInstance
            }

            synchronized(this) {
                Log.d("TAG", "Here - getDatabase: in Synchronized")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDataBase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

