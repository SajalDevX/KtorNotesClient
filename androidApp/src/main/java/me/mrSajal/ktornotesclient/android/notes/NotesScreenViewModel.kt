package me.mrSajal.ktornotesclient.android.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mrSajal.ktornotesclient.common.data.local.UserSettings
import me.mrSajal.ktornotesclient.common.domain.notes.Notes
import me.mrSajal.ktornotesclient.notes.domain.usecases.CreateNoteUseCase
import me.mrSajal.ktornotesclient.notes.domain.usecases.EditNoteUseCase
import me.mrSajal.ktornotesclient.notes.domain.usecases.GetSingleNoteUseCase

class NotesScreenViewModel(
    private val createNoteUseCase: CreateNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val getSingleNoteUseCase: GetSingleNoteUseCase,
) : ViewModel() {
    var notesUiState by mutableStateOf(NotesUiState())
        private set
    var titleTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set
    var contentTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set

    fun createNote() {
        notesUiState = notesUiState.copy(isLoading = true)
        viewModelScope.launch {
            val request = createNoteUseCase(titleTextFieldValue.text,contentTextFieldValue.text)
            notesUiState = if (request.data == true) {
                notesUiState.copy(
                    isLoading = false,
                    success = true,
                    successMessage = request.message
                )
            } else {
                notesUiState.copy(
                    isLoading = false,
                    success = false,
                    errorMessage = request.message
                )
            }
        }
    }

    fun fetchNote(noteId: String) {
        notesUiState = notesUiState.copy(isLoading = true)
        viewModelScope.launch {
            val request = getSingleNoteUseCase(noteId)
            if (request.data != null) {
                notesUiState = notesUiState.copy(
                    isLoading = false,
                    note = request.data
                )
                titleTextFieldValue = titleTextFieldValue.copy(
                    text = notesUiState.note?.noteTitle ?: "",
                    selection = TextRange(index = notesUiState.note?.noteTitle?.length ?: 0)
                )
                contentTextFieldValue = contentTextFieldValue.copy(
                    text = notesUiState.note?.noteContent ?: "",
                    selection = TextRange(index = notesUiState.note?.noteContent?.length ?: 0)
                )

            } else {
                notesUiState = notesUiState.copy(
                    isLoading = false,
                    errorMessage = request.message
                )
            }
        }
    }

    fun onTitleChange(title: TextFieldValue) {
        titleTextFieldValue = title
    }

    fun onContentChange(content: TextFieldValue) {
        contentTextFieldValue = content
    }

    fun editNote(
        noteId: String
    ) {
        notesUiState = notesUiState.copy(isLoading = true)
        viewModelScope.launch {
            val title = titleTextFieldValue.text
            val content = contentTextFieldValue.text
            val result = editNoteUseCase(noteId,title,content )
            notesUiState = if (result.data == true) {
                notesUiState.copy(
                    isLoading = false,
                    success = true,
                    successMessage = result.message
                )
            } else {
                notesUiState.copy(
                    isLoading = false,
                    success = false,
                    errorMessage = result.message
                )
            }
        }
    }
}

data class NotesUiState(
    val isLoading: Boolean = false,
    val note: Notes? = null,
    val success: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

data class TextParams(
    val noteTitle: String,
    val noteContent: String,
)