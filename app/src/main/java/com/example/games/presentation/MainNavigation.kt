package com.example.games.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.games.di.AppComponent
import com.example.games.presentation.screens.gameScreen.GameScreen
import com.example.games.presentation.screens.listScreen.ListScreen
import com.example.games.presentation.utils.daggerViewModel

sealed class Screen(
    val route: String,
) {
    object List : Screen(
        route = "list",
    )

    data class Game(private val gameId: Int) : Screen(
        route = "game/$gameId",
    )
}

@Composable
fun MainNavHost(
    component: AppComponent,
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = Screen.List
) {
    Scaffold(
        topBar = { TopAppBar() }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = startDestination.route,
            Modifier.padding(innerPadding),
        ) {
            composable("list") {
                val viewModel = daggerViewModel {
                    component.listScreenViewModel()
                }
                ListScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable("game/{gameId}") { backStackEntry ->
                val viewModel = daggerViewModel {
                    component.gameScreenViewModel()
                }
                GameScreen(
                    navController = navController,
                    gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull(),
                    viewModel = viewModel
                )
            }
        }
    }
}