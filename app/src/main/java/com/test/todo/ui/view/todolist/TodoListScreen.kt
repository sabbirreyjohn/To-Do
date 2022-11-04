package com.test.todo.ui.view.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.test.todo.model.TodoItem
import com.test.todo.ui.theme.ToDoTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.test.todo.ui.composables.ShowError
import com.test.todo.ui.composables.ShowLoading

@Composable
fun TodoListScreen(navHostController: NavHostController? = null, viewModel: TodoViewModel) {
    ToDoTheme {
        val todoItems by viewModel.todos.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val hasError by viewModel.hasError.collectAsState()

        if (isLoading) {
            ShowLoading()
        } else {
            if (hasError) {
                ShowError()
            } else {
                TodoList(todoItems = todoItems, navHostController = navHostController)
            }
        }


    }
}

@Composable
fun TodoList(todoItems: List<TodoItem>, navHostController: NavHostController? = null) {
    LazyColumn {
        items(todoItems) { todo ->
            TodoRow(todo, navHostController)
        }
    }
}

@Composable
fun TodoRow(todo: TodoItem, navHostController: NavHostController?) {
    Card(
        elevation = 2.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = todo.title, color = Color.Black)
            Text(text = todo.completed.toString(), color = Color.Black)
        }

    }
}


