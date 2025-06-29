package com.android_tech_challenge.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Query("SELECT * FROM apps")
    suspend fun getAll(): List<AppCacheEntity>

    @Query("SELECT * FROM apps WHERE isFavorite = 1")
    suspend fun getFavorites(): List<AppCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apps: List<AppCacheEntity>)

    @Query("UPDATE apps SET isFavorite = :isFavorite WHERE id = :appId")
    suspend fun updateFavorite(appId: Long, isFavorite: Boolean)

    @Query("DELETE FROM apps")
    suspend fun clearAll()
} 