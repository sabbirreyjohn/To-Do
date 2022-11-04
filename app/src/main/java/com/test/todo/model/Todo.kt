package com.test.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val userId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val completed: Boolean

)