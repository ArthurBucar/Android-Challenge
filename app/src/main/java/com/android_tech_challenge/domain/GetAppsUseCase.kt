package com.android_tech_challenge.domain

class GetAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): Result<List<AppEntity>> = repository.getApps()
}

class GetFavoritesUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): List<AppEntity> = repository.getFavorites()
}

class ToggleFavoriteUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(appId: Long, isFavorite: Boolean) = repository.toggleFavorite(appId, isFavorite)
} 