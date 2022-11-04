package com.test.todo.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.todo.R

@Composable
fun ShowError(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                contentDescription = "failed",
                modifier = Modifier.size(128.dp)
            )
            Text(text = message)
        }
    }
}