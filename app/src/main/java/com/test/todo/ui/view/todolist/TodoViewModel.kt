package com.test.todo.ui.view.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.todo.model.TodoItem
import com.test.todo.repository.DataRepository
import com.test.todo.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repo: DataRepository) : ViewModel() {
    private val _todos = MutableStateFlow<List<TodoItem>>(mutableListOf())
    val todos get() = _todos

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading

    private val _hasError = MutableStateFlow(false)
    val hasError get() = _hasError

    init {
        loadTodoItems()
    }

    private fun loadTodoItems() {
        viewModelScope.launch {
            _isLoading.value = true
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
                else -> {

                }
            }
        }
    }

    suspend fun loadTodosFromDb() {
        _isLoading.value = false
        when (val dbStatus = repo.getTodosFromDB()) {
            is Status.Success -> {
                dbStatus.data?.let {
                    _hasError.value = false
                    _todos.value = it
                }
            }
            is Status.Error -> {
                _hasError.value = true
                _todos.value = mutableListOf()
            }
        }
    }

}