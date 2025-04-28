package com.example.myapplication.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class User(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("RoleId") val roleId: String?,
    @SerializedName("Role") val role: Roles?,
    @SerializedName("Url") val url: String?,
    @SerializedName("RefreshToken") val refreshToken: String?,
    @SerializedName("RefreshTokenExpiry") val refreshTokenExpiry: Date?,
    @SerializedName("PlayedHours") val playedHours: Int
)

// Якщо ваш enum Roles виглядає так:
enum class Roles {
    ADMIN, USER
}