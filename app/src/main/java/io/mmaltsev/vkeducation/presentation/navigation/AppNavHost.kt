package io.mmaltsev.vkeducation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vk.presentation.AppListScreen
import io.mmaltsev.vkeducation.presentation.appdetails.AppDetailsScreen
import io.mmaltsev.vkeducation.presentation.applist.*
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "app_list"
    ) {
        composable("app_list") {
            AppListScreen(navController = navController)
        }

        composable(
            route = "app_detail/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: return@composable
            AppDetailsScreen(
                navController = navController,
                appId = appId
            )
        }
    }
}