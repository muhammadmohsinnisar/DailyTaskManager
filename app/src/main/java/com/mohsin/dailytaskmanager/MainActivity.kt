package com.mohsin.dailytaskmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController, auth = auth)
            }
        }

        val user = auth.currentUser
        Log.d("TaskRepository", "Current user: $user")
        if (user == null) {
            Log.e("TaskRepository", "No user logged in, cannot save task")
            return
        }
    }
}
