package com.mohsin.dailytaskmanager.models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsin.dailytaskmanager.data.TaskRepository
import com.mohsin.dailytaskmanager.dataclass.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = mutableStateListOf<Task>()
    val tasks: SnapshotStateList<Task> get() = _tasks

    init {
        repository.listenToTasks(
            onTasksChanged = { updatedTasks ->
                _tasks.clear()
                _tasks.addAll(updatedTasks)
            },
            onError = { error ->
                Log.e("TaskViewModel", "Error loading tasks", error)
            }
        )
    }

    fun addTask(title: String, description: String) {
        val task = Task(title = title, description = description)
        viewModelScope.launch {
            try {
                repository.addTask(task)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Failed to add task", e)
            }
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.deleteTask(task)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Failed to delete task", e)
            }
        }
    }

    fun toggleTaskDone(task: Task) {
        val updatedTask = task.copy(isDone = !task.isDone)
        viewModelScope.launch {
            try {
                repository.updateTask(updatedTask)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Failed to update task", e)
            }
        }
    }
}
