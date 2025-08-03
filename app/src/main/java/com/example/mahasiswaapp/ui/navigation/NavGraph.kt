package com.example.mahasiswaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mahasiswaapp.ui.screens.DetailScreen
import com.example.mahasiswaapp.ui.screens.FormScreen
import com.example.mahasiswaapp.ui.screens.HomeScreen
import com.example.mahasiswaapp.ui.screens.LoginScreen
import com.example.mahasiswaapp.ui.screens.RegisterScreen

import com.example.mahasiswaapp.ui.viewmodel.MahasiswaViewModel

@Composable
fun AppNavigation(viewModel: MahasiswaViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController, viewModel)
        }
        composable("form") {
            FormScreen(navController = navController, id = null, viewModel = viewModel)
        }
        composable(
            route = "form/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            FormScreen(navController = navController, id = id, viewModel = viewModel)
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            DetailScreen(navController = navController, id = id, viewModel = viewModel)
        }
    }
}
