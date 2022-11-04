package com.test.todo.datasource

import com.test.todo.model.TodoItem
import retrofit2.http.GET
import javax.inject.Inject

interface TodoApiInterface {
    @GET("todos")
    suspend fun getTodos(): List<TodoItem>
}


class TodoApiHelperImpl @Inject internal constructor(private val todoApiInterface: TodoApiInterface) :
    TodoApiHelper {
    override suspend fun getTodos(): List<TodoItem> {
        return todoApiInterface.getTodos()
    }
}

interface TodoApiHelper {
    suspend fun getTodos(): List<TodoItem>
}