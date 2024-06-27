package me.mrSajal.ktornotesclient.android.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import me.mrSajal.ktornotesclient.android.common.util.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun Note(
    noteId: String? = null,
    navController: NavController
) {
    val viewModel: NotesScreenViewModel = koinViewModel()
    NotesScreen(
        notesUiState = viewModel.notesUiState,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onCreateButtonClick = { viewModel.createNote() },
        onSucceed = { navController.navigate(Routes.Home.route) },
        onEditButtonClick = { viewModel.editNote(noteId!!) },
        fetchNote = { viewModel.fetchNote(noteId!!) },
        contentTextFieldValue = viewModel.contentTextFieldValue,
        titleTextFieldValue = viewModel.titleTextFieldValue
    )
}