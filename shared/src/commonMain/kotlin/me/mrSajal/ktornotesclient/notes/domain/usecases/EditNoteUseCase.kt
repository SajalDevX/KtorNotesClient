package me.mrSajal.ktornotesclient.notes.domain.usecases

import me.mrSajal.ktornotesclient.common.util.Result
import me.mrSajal.ktornotesclient.notes.domain.NotesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditNoteUseCase : KoinComponent {
    private val repository by inject<NotesRepository>()

    suspend operator fun invoke(
        noteId:String,
        noteTitle: String,
        noteContent: String
    ): Result<Boolean> {
        return repository.editNote(noteId,noteTitle, noteContent)
    }
}