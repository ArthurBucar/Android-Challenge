package com.android_tech_challenge.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class AppCacheEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val packageName: String,
    val icon: String?,
    val graphic: String?,
    val description: String?,
    val developerName: String?,
    val developerWebsite: String?,
    val downloads: String?,
    val rating: Double?,
    val ratingCount: Int?,
    val versionName: String?,
    val size: Long?,
    val isFavorite: Boolean = false
) 