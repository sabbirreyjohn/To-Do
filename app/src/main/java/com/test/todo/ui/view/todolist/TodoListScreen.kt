package com.test.todo.ui.view.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.test.todo.R
import com.test.todo.domain.entities.Todo
import com.test.todo.ui.composables.StateHandler
import com.test.todo.ui.theme.ToDoTheme

@Composable
fun TodoListScreen(navHostController: NavHostController? = null, viewModel: TodoViewModel) {
    ToDoTheme {
        val todos by viewModel.todos.collectAsState()
        StateHandler(status = todos) { todos ->
            TodoList(todos = todos, navHostController = navHostController)
        }
    }
}

@Composable
fun TodoList(todos: List<Todo>, navHostController: NavHostController? = null) {
    LazyColumn {
        items(todos) { todo ->
            TodoRow(todo, navHostController)
        }
    }
}

@Composable
fun TodoRow(todo: Todo, navHostController: NavHostController?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)), elevation = 24.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = stringResource(id = R.string.title, todo.title), color = Color.Black)
            Text(
                text = "${stringResource(id = R.string.status)} ${
                    if (todo.completed) stringResource(
                        id = R.string.completed
                    ) else {
                        stringResource(id = R.string.not_completed)
                    }
                }",
                color = Color.Black
            )
        }
    }
}


