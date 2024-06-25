package me.mrSajal.ktornotesclient.android.auth.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import me.mrSajal.ktornotesclient.android.common.util.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun Login(navController: NavController) {
    val viewModel: LoginViewModel = koinViewModel()
    LoginScreen(
        uiState = viewModel.uiState,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onSignInClick = viewModel::signIn,
        onNavigateToHome = {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) {
                    inclusive = true
                }
            }
        },
        onNavigateToSignup = {
            navController.navigate(Routes.SignUp.route) {
                popUpTo(Routes.Login.route) {
                    inclusive = true
                }
            }
        }
    )
}