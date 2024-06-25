package me.mrSajal.ktornotesclient.android

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.mrSajal.ktornotesclient.android.auth.login.Login
import me.mrSajal.ktornotesclient.android.auth.signup.SignUp
import me.mrSajal.ktornotesclient.android.common.util.Routes

@Composable
fun NavGraph(
    uiState: MainActivityUiState
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Login.route) {
            Login(navController)
        }
        composable(Routes.SignUp.route) {
            SignUp(navController)
        }
        composable(Routes.Home.route) {
            HomeScreen()
        }
    }
    when (uiState) {
        MainActivityUiState.Loading -> {}
        is MainActivityUiState.Success -> {
            LaunchedEffect(key1 = Unit) {
                if (uiState.currentUser.token.isEmpty()) {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Home.route) {
                            inclusive = true
                        }
                    }
                }else{
                    Log.d("NavGraph", "User is logged in with token: ${uiState.currentUser.token} and Notes ${uiState.currentUser.notes}")
                }
            }
        }
    }
}