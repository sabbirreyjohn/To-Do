package com.test.todo.ui.view.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.todo.domain.entities.Todo
import com.test.todo.domain.repository.DataRepository
import com.test.todo.domain.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repo: DataRepository) : ViewModel() {
    private val _todos = MutableStateFlow<Status<MutableList<Todo>>>(Status.Loading())
    val todos get() = _todos

    init {
        loadTodoItems()
    }

     fun loadTodoItems() {
        viewModelScope.launch {
            when (val status = repo.getTodosFromServer()) {
                is Status.Success -> {
                    status.data?.let {
                        repo.insertTodosToDB(it)
                        loadTodosFromDb()
                    }
                }
                is Status.Error -> {
                    loadTodosFromDb()
                }
            }
        }
    }

    suspend fun loadTodosFromDb() {
        val dbStatus = repo.getTodosFromDB()
        _todos.value = dbStatus
    }
}