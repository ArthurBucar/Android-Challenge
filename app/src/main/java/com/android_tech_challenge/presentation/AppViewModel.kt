package com.android_tech_challenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android_tech_challenge.domain.AppEntity
import com.android_tech_challenge.domain.GetAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppUiState(
    val apps: List<AppEntity> = emptyList(),
    val selectedApp: AppEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        loadApps()
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
                    _uiState.value = _uiState.value.copy(
                        error = exception.message ?: "Unknown error occurred",
                        isLoading = false
                    )
                }
        }
    }

    fun selectApp(appId: Long) {
        val app = _uiState.value.apps.find { it.id == appId }
        _uiState.value = _uiState.value.copy(selectedApp = app)
    }

    fun retry() {
        loadApps()
    }
} 