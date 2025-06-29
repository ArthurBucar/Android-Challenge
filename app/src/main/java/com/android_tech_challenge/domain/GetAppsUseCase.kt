package com.android_tech_challenge.domain

class GetAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): Result<List<AppEntity>> = repository.getApps()
} 