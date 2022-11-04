package com.test.todo.ui.view.todolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.test.todo.MainCoroutineRule
import com.test.todo.model.Todo
import com.test.todo.repository.DataRepository
import com.test.todo.utils.Status
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class TodoViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var mRepository: DataRepository


    @Before
    fun setUp() {
        mRepository = mockk()
        todoViewModel = TodoViewModel(mRepository)
    }

    @Test
    fun `load Todo Items`() = mainCoroutineRule.runBlockingTest {
        var list = mutableListOf<Todo>()
        list.add(Todo(1, 1, "This is the first title", true))
        list.add(Todo(1, 2, "This is the second title", true))
        list.add(Todo(1, 3, "This is the third title", true))
        coEvery { mRepository.getTodosFromServer() } returns Status.Success(list)
        coEvery { mRepository.getTodosFromDB() } returns Status.Success(list)
        todoViewModel.loadTodosFromDb()
        assertThat(todoViewModel.todos?.value?.data?.size).isEqualTo(3)
    }

}