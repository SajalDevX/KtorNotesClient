package me.mrSajal.ktornotesclient.android.notes

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var notesUiState by mutableStateOf(NotesUiState())
        private set
    var titleTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set
    var contentTextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set
    private var currentNoteId: String? = null
    var isEdit by mutableStateOf(false)

    init {
        savedStateHandle.get<String>("noteId")?.let { noteId ->
            if (noteId.isNotEmpty()) {
                currentNoteId = noteId
                fetchNoteById(noteId)
                isEdit = true
            }
        }
    }


    fun createNote() {
        notesUiState = notesUiState.copy(isLoading = true)
        viewModelScope.launch {
            val request = createNoteUseCase(titleTextFieldValue.text, contentTextFieldValue.text)
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
    private fun fetchNoteById(noteId: String) {
        viewModelScope.launch {
            try {
                val note = getSingleNoteUseCase(noteId).data
                if (note != null) {
                    titleTextFieldValue = TextFieldValue(text = note.noteTitle)
                    contentTextFieldValue = TextFieldValue(text = note.noteContent)
                } else {
                    Log.e("NotesScreenViewModel", "Note with id $noteId is null")
                }
            } catch (e: Exception) {
                Log.e("NotesScreenViewModel", "Error fetching note with id $noteId", e)
            }
        }
    }

    fun onTitleChange(title: TextFieldValue) {
        titleTextFieldValue = title
    }

    fun onContentChange(content: TextFieldValue) {
        contentTextFieldValue = content
    }

    fun editNote() {
        notesUiState = notesUiState.copy(isLoading = true)
        viewModelScope.launch {
            val title = titleTextFieldValue.text
            val content = contentTextFieldValue.text
            val result = editNoteUseCase(currentNoteId!!, title, content)
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