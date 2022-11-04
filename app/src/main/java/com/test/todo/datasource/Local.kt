package com.test.todo.datasource

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.todo.model.TodoItem

@Dao
interface TodoDao {
    @Query("select * from TodoItem")
    suspend fun getTodo(): List<TodoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<TodoItem>)
}

@Database(entities = [TodoItem::class], version = 1)

abstract class TheDatabase : RoomDatabase() {
    abstract val todoDao: TodoDao
}

private lateinit var INSTANCE: TheDatabase

fun getDatabase(context: Context): TheDatabase {
    synchronized(TheDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TheDatabase::class.java,
                "todoItems.db"
            ).build()
        }
    }
    return INSTANCE
}