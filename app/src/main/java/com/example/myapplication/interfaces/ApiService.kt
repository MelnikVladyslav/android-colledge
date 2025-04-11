package com.example.myapplication.interfaces

import com.example.myapplication.models.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("register/get-users")
    fun getUsers(): Call<List<User>>
}