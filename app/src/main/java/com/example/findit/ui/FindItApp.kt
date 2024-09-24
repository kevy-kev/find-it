package com.example.findit.ui

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.findit.model.FindItViewModel

/*
Kevin Chou
Oregon State University CS 492
2024
 */

enum class Router() {
    Main, ClueFound, Clue, Permissions
}


@Composable
fun FindItApp(
    context: Context,
    viewModel: FindItViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Router.Permissions.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = Router.Permissions.name) {
                PermissionsScreen(
                    context = context,
                    onClickNext = { navController.navigate(Router.Main.name) })
            }
            composable(route = Router.Main.name) {
                MainScreen(onClickNext = { navController.navigate(Router.Clue.name) }, startTimer = { viewModel.startTimer() })
            }
            composable(route = Router.Clue.name) {
                ClueScreen(
                    resetGame = { viewModel.reset() },
                    clue = uiState.currentClue,
                    clueNumber = uiState.currentClueNumber,
                    timer = uiState.timer,
                    goHome = { navController.navigate(Router.Main.name) },
                    goClueFound = { navController.navigate(Router.ClueFound.name) },
                    pauseTimer = { viewModel.pauseTimer() }
                )
            }
            composable(route = Router.ClueFound.name) {
                ClueFoundScreen(
                    clue = uiState.currentClue,
                    clueNumber = uiState.currentClueNumber,
                    setClue = { viewModel.setCurrentClue(it) },
                    goClue = { navController.navigate(Router.Clue.name) },
                    resetGame = { viewModel.reset() },
                    goHome = { navController.navigate(Router.Main.name) },
                    timer = uiState.timer,
                    startTimer = { viewModel.startTimer() }
                )
            }
        }
    }
}