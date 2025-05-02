package com.example.myapplication.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.User
import com.example.myapplication.models.UserRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    var users by mutableStateOf<List<User>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            isLoading = true
            try {
                users = repository.getUsers()
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = e.message
                users = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}
