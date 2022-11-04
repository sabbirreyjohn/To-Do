package com.test.todo.ui.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.todo.ui.theme.ToDoTheme
import com.test.todo.ui.view.todolist.TodoListScreen
import com.test.todo.ui.view.todolist.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                // A surface container using the 'background' color from the theme
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    Column {
        TopAppBar(
            elevation = 2.dp,
            title = { Text(text = "To-do List") }
        )
        val navController = rememberNavController()
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = "TodoList") {
                composable("TodoList") {
                    val viewModel = hiltViewModel<TodoViewModel>()
                    TodoListScreen(navController, viewModel)
                }
            }
        }
    }
}
