package com.android_tech_challenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android_tech_challenge.domain.AppEntity
import com.android_tech_challenge.domain.GetAppsUseCase
import com.android_tech_challenge.domain.GetFavoritesUseCase
import com.android_tech_challenge.domain.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class AppUiState(
    val apps: List<AppEntity> = emptyList(),
    val filteredApps: List<AppEntity> = emptyList(),
    val selectedApp: AppEntity? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: AppError? = null,
    val showFavoritesOnly: Boolean = false
)

sealed class AppError {
    object NetworkError : AppError()
    object ServerError : AppError()
    object CacheError : AppError()
    data class UnknownError(val message: String) : AppError()
}

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        loadApps()
        setupSearchFilter()
    }

    private fun setupSearchFilter() {
        viewModelScope.launch {
            combine(
                _uiState.map { it.apps },
                _uiState.map { it.searchQuery },
                _uiState.map { it.showFavoritesOnly }
            ) { apps: List<AppEntity>, query: String, showFavoritesOnly: Boolean ->
                var filteredApps = apps
                
                // Filter by favorites if needed
                if (showFavoritesOnly) {
                    filteredApps = filteredApps.filter { it.isFavorite }
                }
                
                // Filter by search query
                if (query.isNotBlank()) {
                    filteredApps = filteredApps.filter { app ->
                        app.name.contains(query, ignoreCase = true) ||
                        app.developerName?.contains(query, ignoreCase = true) == true ||
                        app.packageName.contains(query, ignoreCase = true)
                    }
                }
                
                filteredApps
            }.collect { filteredApps ->
                _uiState.value = _uiState.value.copy(filteredApps = filteredApps)
            }
        }
    }

    fun loadApps() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            getAppsUseCase()
                .onSuccess { apps ->
                    _uiState.value = _uiState.value.copy(
                        apps = apps,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    val error = when (exception) {
                        is java.net.UnknownHostException -> AppError.NetworkError
                        is retrofit2.HttpException -> AppError.ServerError
                        else -> AppError.UnknownError(exception.message ?: "Unknown error occurred")
                    }
                    _uiState.value = _uiState.value.copy(
                        error = error,
                        isLoading = false
                    )
                }
        }
    }

    fun refreshApps() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true, error = null)
            getAppsUseCase()
                .onSuccess { apps ->
                    _uiState.value = _uiState.value.copy(
                        apps = apps,
                        isRefreshing = false
                    )
                }
                .onFailure { exception ->
                    val error = when (exception) {
                        is java.net.UnknownHostException -> AppError.NetworkError
                        is retrofit2.HttpException -> AppError.ServerError
                        else -> AppError.UnknownError(exception.message ?: "Unknown error occurred")
                    }
                    _uiState.value = _uiState.value.copy(
                        error = error,
                        isRefreshing = false
                    )
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun toggleFavoritesFilter() {
        _uiState.value = _uiState.value.copy(showFavoritesOnly = !_uiState.value.showFavoritesOnly)
    }

    fun toggleFavorite(appId: Long) {
        viewModelScope.launch {
            val currentApp = _uiState.value.apps.find { it.id == appId }
            currentApp?.let { app ->
                val newFavoriteState = !app.isFavorite
                toggleFavoriteUseCase(appId, newFavoriteState)
                
                // Update the app in the list
                val updatedApps = _uiState.value.apps.map { 
                    if (it.id == appId) it.copy(isFavorite = newFavoriteState) else it 
                }
                _uiState.value = _uiState.value.copy(apps = updatedApps)
            }
        }
    }

    fun selectApp(appId: Long) {
        val app = _uiState.value.apps.find { it.id == appId }
        _uiState.value = _uiState.value.copy(selectedApp = app)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun retry() {
        loadApps()
    }
} 