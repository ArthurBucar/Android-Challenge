package com.android_tech_challenge

import com.android_tech_challenge.domain.AppEntity
import com.android_tech_challenge.domain.GetAppsUseCase
import com.android_tech_challenge.domain.GetFavoritesUseCase
import com.android_tech_challenge.domain.ToggleFavoriteUseCase
import com.android_tech_challenge.presentation.AppError
import com.android_tech_challenge.presentation.AppUiState
import com.android_tech_challenge.presentation.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {
    
    private lateinit var viewModel: AppViewModel
    private lateinit var getAppsUseCase: GetAppsUseCase
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getAppsUseCase = mock()
        getFavoritesUseCase = mock()
        toggleFavoriteUseCase = mock()
        viewModel = AppViewModel(getAppsUseCase, getFavoritesUseCase, toggleFavoriteUseCase)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `when loading apps successfully, should update ui state with apps`() = runTest {
        // Given
        val mockApps = listOf(
            AppEntity(
                id = 1L,
                name = "Test App",
                packageName = "com.test.app",
                icon = "test-icon.png",
                graphic = null,
                description = "Test description",
                developerName = "Test Developer",
                developerWebsite = null,
                downloads = "1000",
                rating = 4.5,
                ratingCount = 100,
                versionName = "1.0.0",
                size = 1024L,
                isFavorite = false
            )
        )
        whenever(getAppsUseCase()).thenReturn(Result.success(mockApps))
        
        // When
        viewModel.loadApps()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(mockApps, uiState.apps)
        assertFalse(uiState.isLoading)
        assertEquals(null, uiState.error)
    }
    
    @Test
    fun `when loading apps fails, should update ui state with error`() = runTest {
        // Given
        val exception = Exception("Network error")
        whenever(getAppsUseCase()).thenReturn(Result.failure(exception))
        
        // When
        viewModel.loadApps()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        val uiState = viewModel.uiState.first()
        assertTrue(uiState.apps.isEmpty())
        assertFalse(uiState.isLoading)
        assertTrue(uiState.error is AppError.UnknownError)
    }
    
    @Test
    fun `when updating search query, should filter apps`() = runTest {
        // Given
        val mockApps = listOf(
            AppEntity(
                id = 1L,
                name = "Test App",
                packageName = "com.test.app",
                icon = null,
                graphic = null,
                description = null,
                developerName = "Test Developer",
                developerWebsite = null,
                downloads = null,
                rating = null,
                ratingCount = null,
                versionName = null,
                size = null,
                isFavorite = false
            ),
            AppEntity(
                id = 2L,
                name = "Another App",
                packageName = "com.another.app",
                icon = null,
                graphic = null,
                description = null,
                developerName = "Another Developer",
                developerWebsite = null,
                downloads = null,
                rating = null,
                ratingCount = null,
                versionName = null,
                size = null,
                isFavorite = false
            )
        )
        whenever(getAppsUseCase()).thenReturn(Result.success(mockApps))
        
        // When
        viewModel.loadApps()
        viewModel.updateSearchQuery("Test")
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(1, uiState.filteredApps.size)
        assertEquals("Test App", uiState.filteredApps[0].name)
    }
    
    @Test
    fun `when toggling favorites filter, should update showFavoritesOnly`() = runTest {
        // Given
        val initialUiState = viewModel.uiState.first()
        assertFalse(initialUiState.showFavoritesOnly)
        
        // When
        viewModel.toggleFavoritesFilter()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        val updatedUiState = viewModel.uiState.first()
        assertTrue(updatedUiState.showFavoritesOnly)
    }
    
    @Test
    fun `when clearing error, should remove error from ui state`() = runTest {
        // Given
        val exception = Exception("Test error")
        whenever(getAppsUseCase()).thenReturn(Result.failure(exception))
        viewModel.loadApps()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val uiStateWithError = viewModel.uiState.first()
        assertTrue(uiStateWithError.error != null)
        
        // When
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        val uiStateWithoutError = viewModel.uiState.first()
        assertEquals(null, uiStateWithoutError.error)
    }
} 