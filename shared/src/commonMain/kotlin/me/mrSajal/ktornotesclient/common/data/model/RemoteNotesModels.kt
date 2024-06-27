package me.mrSajal.ktornotesclient.common.data.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrSajal.ktornotesclient.common.domain.notes.Notes

@Serializable
data class InsertNoteParams(
    val userId: String,
    val noteTitle: String,
    val noteContent: String
)

@Serializable
data class RemoteNotes(
    val noteId: String,
    val noteTitle: String = "",
    val noteContent: String = "",
    val createdAt: Long
) {
    fun toDomainNotes(): Notes {
        return Notes(
            noteId, noteTitle, noteContent, createdAt
        )
    }
}

@Serializable
data class EditNoteParams(
    val noteId: String,
    val userId: String,
    val noteTitle: String,
    val noteContent: String
)

@Serializable
data class RemoteNotesParams(
    val userId: String,
    val noteId: String
)

@Serializable
data class NotesApiResponseData(
    val success: Boolean,
    val note: RemoteNotes? = null,
    val notes: List<RemoteNotes> = listOf(),
    val message: String? = null
)

data class NotesApiResponse(
    val code: HttpStatusCode,
    val data: NotesApiResponseData
)