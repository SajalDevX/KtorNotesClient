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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
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
    isEdit: Boolean,
    onSucceed: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(
        key1 = notesUiState.success,
        key2 = notesUiState.errorMessage
    ) {
        if (notesUiState.success) {
            onSucceed()
        }
        if (notesUiState.errorMessage != null) {
            Toast.makeText(context, notesUiState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        NoteTextField(
            value = titleTextFieldValue,
            onValueChange = onTitleChange,
            placeholder = stringResource(id = R.string.note_title)
        )
        Spacer(modifier = Modifier.height(8.dp))
        NoteTextField(
            value = contentTextFieldValue,
            onValueChange = onContentChange,
            placeholder = stringResource(id = R.string.note_content)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (notesUiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (!isEdit) {
                        onCreateButtonClick()
                    } else {
                        onEditButtonClick()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = if (!isEdit) "Create Note" else "Edit Note")
            }
        }
    }
}

@Composable
fun NoteTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        value = value,
        onValueChange = { newValue ->
            onValueChange(
                newValue
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
                text = placeholder,
                style = MaterialTheme.typography.body2
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}



