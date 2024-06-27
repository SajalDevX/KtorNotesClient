package me.mrSajal.ktornotesclient.android.notes

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.mrSajal.ktornotesclient.android.R
import me.mrSajal.ktornotesclient.android.common.theming.Gray

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    notesUiState: NotesUiState,
    titleTextFieldValue: TextFieldValue,
    contentTextFieldValue: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    onContentChange: (TextFieldValue) -> Unit,
    onCreateButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    onSucceed: () -> Unit,
    fetchNote: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        fetchNote()
    }
    LaunchedEffect(
        key1 = notesUiState.success,
        key2 = notesUiState.errorMessage,
        block = {
            if (notesUiState.success){
                onSucceed()
            }

            if (notesUiState.note != null && notesUiState.errorMessage != null){
                Toast.makeText(context, notesUiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
fun NoteTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        value = value,
        onValueChange = {
            onValueChange(
                TextFieldValue(
                    text = it.text,
                )
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colors.surface
            } else {
                Gray
            },
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.body2,
        placeholder = {
            Text(
                text = stringResource(id = R.string.user_note_content),
                style = MaterialTheme.typography.body2
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}



