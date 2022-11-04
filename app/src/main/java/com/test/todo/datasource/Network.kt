package com.test.todo.datasource

import com.test.todo.model.Todo
import retrofit2.http.GET
import javax.inject.Inject

interface TodoApiInterface {
    @GET("todos")
    suspend fun getTodos(): MutableList<Todo>
}


class TodoApiHelperImpl @Inject internal constructor(private val todoApiInterface: TodoApiInterface) :
    TodoApiHelper {
    override suspend fun getTodos(): MutableList<Todo> {
        return todoApiInterface.getTodos()
    }
}

interface TodoApiHelper {
    suspend fun getTodos(): MutableList<Todo>
}