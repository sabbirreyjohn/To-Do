package com.test.todo.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.test.todo.datasource.TheDatabase
import com.test.todo.model.Todo
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class DatabaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TheDatabase
    private lateinit var todos: MutableList<Todo>

    @Before
    fun initDb() {
        todos = mutableListOf(
            Todo(1, 1, "This is the first title", true),
            Todo(1, 2, "This is the second title", true),
            Todo(1, 3, "This is the third title", true)
        )

        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TheDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun `inserts todoItems successfully`() {
        runBlockingTest {
            database.todoDao.insertAll(todos)
            val todos = database.todoDao.getTodo()
            assertThat(todos.size).isEqualTo(3)
        }
    }

    @Test
    fun `update todoItem if the item already exists`() {
        runBlockingTest {
            database.todoDao.insertAll(todos)
            var tempTodo = Todo(1, 1, "This is the replaced Title", true)
            database.todoDao.insertTodo(tempTodo)
            val todos = database.todoDao.getTodo()
            assertThat(todos.size).isEqualTo(3)
        }
    }
}