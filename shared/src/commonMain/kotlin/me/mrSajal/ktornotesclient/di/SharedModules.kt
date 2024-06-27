package me.mrSajal.ktornotesclient.di

import me.mrSajal.ktornotesclient.auth.data.AuthRepositoryImpl
import me.mrSajal.ktornotesclient.auth.data.AuthService
import me.mrSajal.ktornotesclient.auth.domain.repository.AuthRepository
import me.mrSajal.ktornotesclient.auth.domain.usecase.SignInUseCase
import me.mrSajal.ktornotesclient.auth.domain.usecase.SignUpUseCase
import me.mrSajal.ktornotesclient.common.data.remote.NotesApiService
import me.mrSajal.ktornotesclient.common.util.provideDispatcher
import me.mrSajal.ktornotesclient.notes.data.NotesRepositoryImpl
import me.mrSajal.ktornotesclient.notes.domain.NotesRepository
import me.mrSajal.ktornotesclient.notes.domain.usecases.CreateNoteUseCase
import me.mrSajal.ktornotesclient.notes.domain.usecases.DeleteNoteUseCase
import me.mrSajal.ktornotesclient.notes.domain.usecases.EditNoteUseCase
import me.mrSajal.ktornotesclient.notes.domain.usecases.GetAllNotesUseCase
import me.mrSajal.ktornotesclient.notes.domain.usecases.GetSingleNoteUseCase
import org.koin.dsl.module


private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}
private val notesModule = module {
    factory { NotesApiService() }
    single<NotesRepository> { NotesRepositoryImpl(get(), get(), get()) }
    factory { GetAllNotesUseCase() }
    factory { GetSingleNoteUseCase() }
    factory { DeleteNoteUseCase() }
    factory { CreateNoteUseCase() }
    factory { EditNoteUseCase() }
}

fun getSharedModules() = listOf(authModule, utilityModule, platformModule, notesModule)