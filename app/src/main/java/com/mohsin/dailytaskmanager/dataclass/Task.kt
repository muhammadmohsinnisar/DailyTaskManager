package com.mohsin.dailytaskmanager.dataclass

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val isDone: Boolean = false
)
