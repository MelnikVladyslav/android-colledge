package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.models.User
import com.example.myapplication.objects.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityGetUser : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                GetUserScreen()
            }
        }
    }

    @Composable
    fun GetUserScreen() {
        var users by remember { mutableStateOf<List<User>>(emptyList()) }
        var isLoading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        // Викликаємо API і отримуємо список користувачів
        LaunchedEffect(Unit) {
            fetchUsers { fetchedUsers, error ->
                users = fetchedUsers
                isLoading = false
                errorMessage = error
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(
                    text = "Помилка: $errorMessage",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(users) { user ->
                        UserItem(user)
                    }
                }
            }
        }
    }

    @Composable
    fun UserItem(user: User) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Name: ${user.firstName} ${user.lastName}")
                Text("Role: ${user.role}")
                Text("Played Hours: ${user.playedHours}")
            }
        }
    }

    fun fetchUsers(callback: (List<User>, String?) -> Unit) {
        RetrofitClient.apiService.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(it, null)
                    }
                } else {
                    callback(emptyList(), "Не вдалося отримати дані користувачів.")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(emptyList(), t.message)
            }
        })
    }

    @Preview(showBackground = true)
    @Composable
    fun GetUserView() {
        GetUserScreen()
    }