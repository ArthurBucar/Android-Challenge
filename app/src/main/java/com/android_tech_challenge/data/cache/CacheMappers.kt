package com.android_tech_challenge.data.cache

import com.android_tech_challenge.domain.AppEntity

fun AppCacheEntity.toDomain(): AppEntity = AppEntity(
    id = id,
    name = name,
    packageName = packageName,
    icon = icon,
    graphic = graphic,
    description = description,
    developerName = developerName,
    developerWebsite = developerWebsite,
    downloads = downloads,
    rating = rating,
    ratingCount = ratingCount,
    versionName = versionName,
    size = size,
    isFavorite = isFavorite
)

fun AppEntity.toCache(): AppCacheEntity = AppCacheEntity(
    id = id,
    name = name,
    packageName = packageName,
    icon = icon,
    graphic = graphic,
    description = description,
    developerName = developerName,
    developerWebsite = developerWebsite,
    downloads = downloads,
    rating = rating,
    ratingCount = ratingCount,
    versionName = versionName,
    size = size,
    isFavorite = isFavorite
) 