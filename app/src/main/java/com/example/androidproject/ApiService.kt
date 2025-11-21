package com.example.androidproject

// ApiService.kt
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}
