package com.android_tech_challenge.data

import com.android_tech_challenge.data.cache.AppDao
import com.android_tech_challenge.data.cache.toCache
import com.android_tech_challenge.data.cache.toDomain
import com.android_tech_challenge.domain.AppEntity
import com.android_tech_challenge.domain.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepositoryImpl(
    private val api: AptoideApi,
    private val dao: AppDao
) : AppRepository {
    override suspend fun getApps(): Result<List<AppEntity>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getApps()
            val apps = response.responses
                ?.listApps
                ?.datasets
                ?.all
                ?.data
                ?.list
                ?.map { it.toEntity() } ?: emptyList()
            if (apps.isNotEmpty()) {
                cacheApps(apps)
                Result.success(apps)
            } else {
                // If nothing comes from API, return cache
                val cached = getCachedApps()
                if (cached.isNotEmpty()) Result.success(cached)
                else Result.failure(Exception("No apps found in response"))
            }
        } catch (e: Exception) {
            // In case of error, return cache
            val cached = getCachedApps()
            if (cached.isNotEmpty()) Result.success(cached)
            else Result.failure(e)
        }
    }

    override suspend fun getCachedApps(): List<AppEntity> = withContext(Dispatchers.IO) {
        dao.getAll().map { it.toDomain() }
    }

    override suspend fun getFavorites(): List<AppEntity> = withContext(Dispatchers.IO) {
        dao.getFavorites().map { it.toDomain() }
    }

    override suspend fun cacheApps(apps: List<AppEntity>) = withContext(Dispatchers.IO) {
        dao.clearAll()
        dao.insertAll(apps.map { it.toCache() })
    }

    override suspend fun toggleFavorite(appId: Long, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        dao.updateFavorite(appId, isFavorite)
    }
} 