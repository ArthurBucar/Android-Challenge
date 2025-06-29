package com.android_tech_challenge.data

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class AppRepository {
    private val api: AptoideApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ws2.aptoide.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AptoideApi::class.java)
    }

    suspend fun getApps(): Result<List<App>> { 
        return try {
            val response = api.getApps()
            Log.d("AppRepository", "DEBUG: responses = ${response.responses}")
            Log.d("AppRepository", "DEBUG: listApps = ${response.responses?.listApps}")
            Log.d("AppRepository", "DEBUG: datasets = ${response.responses?.listApps?.datasets}")
            Log.d("AppRepository", "DEBUG: all = ${response.responses?.listApps?.datasets?.all}")
            Log.d("AppRepository", "DEBUG: data = ${response.responses?.listApps?.datasets?.all?.data}")
            Log.d("AppRepository", "DEBUG: list = ${response.responses?.listApps?.datasets?.all?.data?.list}")
            val apps = response.responses
                ?.listApps
                ?.datasets
                ?.all
                ?.data
                ?.list ?: emptyList()
            if (apps.isEmpty()) {
                Log.w("AppRepository", "No apps found in response")
                return Result.failure(Exception("No apps found in response"))
            }
            Log.d("AppRepository", "Apps count: ${apps.size}")
            Result.success(apps)
        } catch (e: Exception) {
            Log.e("AppRepository", "Error fetching apps", e)
            Result.failure(e)
        }
    }
} 