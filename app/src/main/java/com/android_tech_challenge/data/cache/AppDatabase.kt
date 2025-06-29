package com.android_tech_challenge.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AppCacheEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
} 