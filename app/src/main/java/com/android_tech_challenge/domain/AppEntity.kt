package com.android_tech_challenge.domain

data class AppEntity(
    val id: Long,
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
    val size: Long?
) 