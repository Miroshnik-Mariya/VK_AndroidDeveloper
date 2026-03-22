package com.example.vk.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.mmaltsev.vkeducation.presentation.appdetails.AppDetailsScreen  // экран из форка

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "app_list"  // Стартуем с экрана списка
    ) {
        // Экран списка приложений
        composable("app_list") {
            AppListScreen(navController = navController)
        }

        // Экран деталей приложения
        composable(
            route = "app_detail/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.IntType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getInt("appId") ?: 0
            AppDetailsScreen()  // экран из форкнутого проекта
        }
    }
}