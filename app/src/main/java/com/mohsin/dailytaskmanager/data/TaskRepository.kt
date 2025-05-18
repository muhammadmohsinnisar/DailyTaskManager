package com.mohsin.dailytaskmanager.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.mohsin.dailytaskmanager.dataclass.Task
import kotlinx.coroutines.tasks.await

class TaskRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private fun userTasksCollection() =
        firestore.collection("users")
            .document(auth.currentUser?.uid ?: "")
            .collection("tasks")

    suspend fun addTask(task: Task) {
        val user = auth.currentUser ?: return
        val taskId = task.id.ifEmpty { userTasksCollection().document().id }
        val taskWithId = task.copy(id = taskId)
        userTasksCollection().document(taskId).set(taskWithId).await()
    }

    suspend fun updateTask(task: Task) {
        val user = auth.currentUser ?: return
        userTasksCollection().document(task.id).set(task).await()
    }

    suspend fun deleteTask(task: Task) {
        val user = auth.currentUser ?: return
        userTasksCollection().document(task.id).delete().await()
    }

    fun listenToTasks(
        onTasksChanged: (List<Task>) -> Unit,
        onError: (Exception) -> Unit
    ): ListenerRegistration? {
        val user = auth.currentUser ?: return null
        return userTasksCollection()
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }
                val tasks = snapshot?.toObjects(Task::class.java) ?: emptyList()
                onTasksChanged(tasks)
            }
    }
}
