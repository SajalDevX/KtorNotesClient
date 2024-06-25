package me.mrSajal.ktornotesclient.android.auth.signup

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import me.mrSajal.ktornotesclient.android.common.util.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUp(navController: NavController) {
    val viewModel: SignUpViewModel = koinViewModel()
    SignUpScreen(
        uiState = viewModel.uiState,
        onUsernameChange = viewModel::updateUsername,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onNavigateToLogin = {
            navController.navigate(Routes.Login.route){
                popUpTo(Routes.SignUp.route){
                    inclusive = true
                }
            }
        },
        onNavigateToHome = {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) {
                    inclusive = true
                }
            }
        },
        onSignUpClick = viewModel::signUp
    )
}










