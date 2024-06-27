package me.mrSajal.ktornotesclient.notes.domain.usecases

import me.mrSajal.ktornotesclient.common.domain.notes.Notes
import me.mrSajal.ktornotesclient.common.util.Result
import me.mrSajal.ktornotesclient.notes.domain.NotesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetSingleNoteUseCase : KoinComponent {
    private val repository by inject<NotesRepository>()

    suspend operator fun invoke(
        noteId:String
    ): Result<Notes> {
        return repository.getSingleNote(noteId)
    }
}