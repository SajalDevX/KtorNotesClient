package me.mrSajal.ktornotesclient.android.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import me.mrSajal.ktornotesclient.android.common.util.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(
    navController: NavController
) {
    val viewModel: HomeScreenViewModel = koinViewModel()

    HomeScreen(
        homeUiState = viewModel.homeUiState,
        homeRefreshState = viewModel.homeRefreshState,
        onUiAction = { viewModel.onUiAction(it) },
        onAddNoteClick = {
            navController.navigate(Routes.Notes.route)
        },
        navController = navController,
        fetchNote = {viewModel.fetchData()}
    )
}