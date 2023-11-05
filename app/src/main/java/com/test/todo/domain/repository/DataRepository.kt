package com.test.todo.domain.repository

import com.test.todo.data.TheDatabase
import com.test.todo.data.TodoApiHelper
import com.test.todo.domain.entities.Todo
import com.test.todo.domain.utils.Status
import javax.inject.Inject

class DataRepository @Inject internal constructor(
    private val database: TheDatabase,
    private val todoApiHelper: TodoApiHelper
) {

    suspend fun getTodosFromServer(): Status<MutableList<Todo>> {
        val response = try {
            todoApiHelper.getTodos()
        } catch (e: Exception) {
            return Status.Error("An unknown error occured.")
        }
        return Status.Success(response)
    }

    suspend fun getTodosFromDB(): Status<MutableList<Todo>> {
        val response = try {
            database.todoDao.getTodo()
        } catch (e: Exception) {
            return Status.Error("Failed to load from database")
        }
        if (response.isEmpty())
            return Status.Error("Database is empty")
        return Status.Success(response)
    }

    suspend fun insertTodosToDB(todos: MutableList<Todo>) = database.todoDao.insertAll(todos)

}