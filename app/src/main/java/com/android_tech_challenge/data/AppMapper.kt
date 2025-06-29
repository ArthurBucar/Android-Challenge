package com.android_tech_challenge.data

import com.android_tech_challenge.domain.AppEntity

fun App.toEntity(): AppEntity = AppEntity(
    id = this.id ?: 0L,
    name = this.name ?: "",
    packageName = this.packageName ?: "",
    icon = this.icon,
    graphic = this.graphic,
    description = this.description,
    developerName = this.developer?.name,
    developerWebsite = this.developer?.website,
    downloads = this.stats?.downloads,
    rating = this.stats?.rating?.average,
    ratingCount = this.stats?.rating?.total,
    versionName = this.file?.versionName,
    size = this.file?.size,
    isFavorite = false
) 