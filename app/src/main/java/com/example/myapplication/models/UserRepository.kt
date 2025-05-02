package com.example.myapplication.models

import com.example.myapplication.interfaces.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {
    suspend fun getUsers(): List<User> {
        val response = apiService.getUsersSuspended()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Код помилки: ${response.code()}")
        }
    }
}

