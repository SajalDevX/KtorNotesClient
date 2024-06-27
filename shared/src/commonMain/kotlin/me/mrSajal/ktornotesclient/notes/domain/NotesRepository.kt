package me.mrSajal.ktornotesclient.notes.domain

import me.mrSajal.ktornotesclient.common.domain.notes.Notes
import me.mrSajal.ktornotesclient.common.util.Result

interface NotesRepository {
    suspend fun getSingleNote(noteId: String): Result<Notes>
    suspend fun getAllNotes(page: Int, pageSize: Int): Result<List<Notes>>
    suspend fun deleteNote(noteId: String): Result<Boolean>
    suspend fun insertNote(
        noteTitle: String,
        noteContent: String
    ): Result<Boolean>

    suspend fun editNote(noteId: String, noteTitle: String, noteContent: String): Result<Boolean>
}
