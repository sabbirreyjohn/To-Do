package com.test.todo.repository

import com.test.todo.datasource.TheDatabase
import com.test.todo.datasource.TodoApiHelper
import com.test.todo.model.TodoItem
import com.test.todo.utils.Status
import javax.inject.Inject

class DataRepository @Inject internal constructor(
    private val database: TheDatabase,
    private val todoApiHelper: TodoApiHelper
) {

    suspend fun getTodosFromServer(): Status<List<TodoItem>> {
        val response = try {
            todoApiHelper.getTodos()
        } catch (e: Exception) {
            return Status.Error("An unknown error occured.")
        }
        return Status.Success(response)
    }

    suspend fun getTodosFromDB(): Status<List<TodoItem>> {
        val response = try {
            database.todoDao.getTodo()
        } catch (e: Exception) {
            return Status.Error("Failed to load from database")
        }
        if (response.isEmpty())
            return Status.Error("Database is empty")
        return Status.Success(response)
    }

    suspend fun insertTodosToDB(todos: List<TodoItem>) = database.todoDao.insertAll(todos)

}