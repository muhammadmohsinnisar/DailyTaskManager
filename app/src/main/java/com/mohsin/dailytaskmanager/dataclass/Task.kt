package com.mohsin.dailytaskmanager.dataclass

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val done: Boolean = false
)
