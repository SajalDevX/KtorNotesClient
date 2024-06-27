package me.mrSajal.ktornotesclient.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.mrSajal.ktornotesclient.common.domain.notes.Notes
import me.mrSajal.ktornotesclient.notes.domain.usecases.DeleteNoteUseCase
import me.mrSajal.ktornotesclient.notes.domain.usecases.GetAllNotesUseCase

class HomeScreenViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set
    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set

    init {
        fetchData()
    }

    private fun fetchData() {
        homeRefreshState = homeRefreshState.copy(isRefreshing = true)
        viewModelScope.launch {
            delay(1000)
            try {
                val notes = getAllNotesUseCase(0, 10)
                val notesData = notes.data

                homeUiState = if (notesData != null) {
                    homeUiState.copy(
                        isLoading = false,
                        notes = notesData,
                    )
                } else {
                    homeUiState.copy(
                        isLoading = false,
                        errorMessage = "Failed to load notes: Data is null"
                    )
                }
            } catch (e: Exception) {
                homeUiState = homeUiState.copy(
                    isLoading = false,
                    errorMessage = "An error occurred: ${e.message}"
                )
            } finally {
                homeRefreshState = homeRefreshState.copy(isRefreshing = false)
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            try {
                val request = deleteNoteUseCase(noteId)
                homeUiState = if (request.data == true) {
                    homeUiState.copy(
                        notes = homeUiState.notes.filter { it.noteId != noteId },
                        isLoading = false,
                        success = true,
                        successMessage = request.message
                    )
                } else {
                    homeUiState.copy(
                        isLoading = false,
                        success = false,
                        errorMessage = request.message
                    )
                }
            } catch (e: Exception) {
                homeUiState = homeUiState.copy(
                    isLoading = false,
                    errorMessage = "An error occurred: ${e.message}"
                )
            }
        }
    }


    fun onUiAction(uiAction: HomeUiAction) {
        when (uiAction) {
            is HomeUiAction.DeleteNoteAction -> deleteNote(noteId = uiAction.id)
            HomeUiAction.RefreshAction -> fetchData()
            HomeUiAction.LoadMorePostsAction -> TODO()
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val notes: List<Notes> = emptyList(),
    val success: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

data class HomeRefreshState(
    val isRefreshing: Boolean = false,
    val refreshErrorMessage: String? = null
)

sealed interface HomeUiAction {
    data class DeleteNoteAction(val id: String) : HomeUiAction
    data object RefreshAction : HomeUiAction
    data object LoadMorePostsAction : HomeUiAction
}
