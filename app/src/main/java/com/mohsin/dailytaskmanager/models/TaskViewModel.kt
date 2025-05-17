package com.mohsin.dailytaskmanager.models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.mohsin.dailytaskmanager.dataclass.Task

class TaskViewModel : ViewModel() {

    private var nextId = 1

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> = _tasks

    fun addTask(title: String, description: String) {
        val task = Task(nextId++, title, description)
        _tasks.add(task)
    }

    fun removeTask(task: Task) {
        _tasks.remove(task)
    }

    fun toggleTaskDone(task: Task) {
        val index = _tasks.indexOf(task)
        if (index != -1) {
            _tasks[index] = task.copy(isDone = !task.isDone)
        }
    }
}