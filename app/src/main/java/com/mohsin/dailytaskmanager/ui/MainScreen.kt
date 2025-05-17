package com.mohsin.dailytaskmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohsin.dailytaskmanager.models.TaskViewModel

@Composable
fun MainScreen(viewModel: TaskViewModel) {
    val tasks by remember { derivedStateOf { viewModel.tasks } }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Task Manager", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    viewModel.addTask(title, description)
                    title = ""
                    description = ""
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Add Task")
        }

        Divider()

        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onToggleDone = { viewModel.toggleTaskDone(task) },
                    onDelete = { viewModel.removeTask(task) }
                )
            }
        }
    }
}
