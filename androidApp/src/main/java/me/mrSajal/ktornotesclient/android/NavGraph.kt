package me.mrSajal.ktornotesclient.android

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.mrSajal.ktornotesclient.android.auth.login.Login
import me.mrSajal.ktornotesclient.android.auth.signup.SignUp
import me.mrSajal.ktornotesclient.android.common.util.Routes
import me.mrSajal.ktornotesclient.android.home.Home
import me.mrSajal.ktornotesclient.android.home.HomeScreen
import me.mrSajal.ktornotesclient.android.notes.Note
import me.mrSajal.ktornotesclient.android.notes.NotesScreen
import me.mrSajal.ktornotesclient.common.domain.notes.Notes
import okhttp3.Route

@Composable
fun NavGraph(
    uiState: MainActivityUiState
) {
    val navController = rememberNavController()

    if (uiState is MainActivityUiState.Success) {
        val startDestination = if (uiState.currentUser.token.isNotEmpty()) {
            Routes.Home.route
        } else {
            Routes.Login.route
        }

        NavHost(navController = navController, startDestination = startDestination) {
            composable(Routes.Login.route) {
                Login(navController)
            }
            composable(Routes.SignUp.route) {
                SignUp(navController)
            }
            composable(Routes.Home.route) {
                Home(navController)
            }
            composable(
                route = "${Routes.Notes.route}/{${Routes.Notes.ARG_NOTE_ID}}",
                arguments = listOf(navArgument(Routes.Notes.ARG_NOTE_ID) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString(Routes.Notes.ARG_NOTE_ID)
                Note(noteId = noteId, navController = navController)
            }
            composable(Routes.Notes.route) {
                Note(navController = navController) // This screen could be for adding a new note
            }
        }

        LaunchedEffect(key1 = uiState.currentUser.token) {
            if (uiState.currentUser.token.isEmpty()) {
                navController.navigate(Routes.Login.route) {
                    popUpTo(Routes.Home.route) {
                        inclusive = true
                    }
                }
            } else {
                Log.d(
                    "NavGraph",
                    "User is logged in with token: ${uiState.currentUser.token} and Notes ${uiState.currentUser.notes}"
                )
            }
        }
    }
}
