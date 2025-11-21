package com.example.androidproject

data class LoginResponse(
    val status: String?,
    val success: Boolean,
    val message: String,
    val token: String? = null
)
