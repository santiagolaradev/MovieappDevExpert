package com.example.movieappdevexpert.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappdevexpert.ui.screens.detail.DetailScreen
import com.example.movieappdevexpert.ui.screens.detail.DetailViewModel
import com.example.movieappdevexpert.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(onMovieClick = { movies ->
                navController.navigate(NavScreen.Detail.createRoute(movies.id))
            })
        }
        composable(
            route = NavScreen.Detail.route, //1. movieId: argumento que se pasan a la pantalla de detalle
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailScreen(
                viewModel { DetailViewModel(id = movieId) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
