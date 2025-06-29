package com.android_tech_challenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android_tech_challenge.presentation.AppViewModel
import com.android_tech_challenge.ui.screens.AppDetailsScreen
import com.android_tech_challenge.ui.screens.AppListScreen

sealed class Screen(val route: String) {
    object AppList : Screen("app_list")
    object AppDetails : Screen("app_details/{appId}") {
        fun createRoute(appId: Long) = "app_details/$appId"
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = Screen.AppList.route
    ) {
        composable(Screen.AppList.route) {
            AppListScreen(
                onAppClick = { app ->
                    app.id?.let { id ->
                        viewModel.selectApp(id)
                        navController.navigate(Screen.AppDetails.createRoute(id))
                    }
                },
                viewModel = viewModel
            )
        }
        
        composable(Screen.AppDetails.route) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId")?.toLongOrNull()
            
            // Select the app if not already selected
            if (appId != null && uiState.selectedApp?.id != appId) {
                viewModel.selectApp(appId)
            }
            
            uiState.selectedApp?.let { app ->
                AppDetailsScreen(
                    app = app,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
} 