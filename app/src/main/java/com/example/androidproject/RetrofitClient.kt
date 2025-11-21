package com.example.androidproject


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL = "https://ai-plant-pesticide-sprinkler-system.onrender.com/api/v1/"

    val instance: ApiService by lazy {
		val logging = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
		val client = OkHttpClient.Builder()
			.addInterceptor(logging)
			.build()

		Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
			.client(client)
            .build()
            .create(ApiService::class.java)
    }
}
