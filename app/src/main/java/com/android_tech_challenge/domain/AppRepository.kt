package com.android_tech_challenge.domain

interface AppRepository {
    suspend fun getApps(): Result<List<AppEntity>>
    suspend fun getCachedApps(): List<AppEntity>
    suspend fun cacheApps(apps: List<AppEntity>)
} 