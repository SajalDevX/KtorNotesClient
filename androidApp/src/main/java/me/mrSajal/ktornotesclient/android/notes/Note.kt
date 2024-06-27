package me.mrSajal.ktornotesclient.android.notes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import me.mrSajal.ktornotesclient.android.common.util.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun Note(
    navController: NavController
) {
    val viewModel: NotesScreenViewModel = koinViewModel()

    NotesScreen(
        notesUiState = viewModel.notesUiState,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onCreateButtonClick = { viewModel.createNote() },
        onSucceed = { navController.navigateUp() },
        onEditButtonClick = { viewModel.editNote() },
        isEdit = viewModel.isEdit,
        contentTextFieldValue = viewModel.contentTextFieldValue,
        titleTextFieldValue = viewModel.titleTextFieldValue
    )
}