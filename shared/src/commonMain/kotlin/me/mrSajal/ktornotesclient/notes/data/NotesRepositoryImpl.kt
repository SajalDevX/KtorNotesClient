package me.mrSajal.ktornotesclient.notes.data

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrSajal.ktornotesclient.common.data.local.UserPreferences
import me.mrSajal.ktornotesclient.common.data.model.EditNoteParams
import me.mrSajal.ktornotesclient.common.data.model.InsertNoteParams
import me.mrSajal.ktornotesclient.common.data.model.RemoteNotesParams
import me.mrSajal.ktornotesclient.common.data.remote.NotesApiService
import me.mrSajal.ktornotesclient.common.domain.notes.Notes
import me.mrSajal.ktornotesclient.common.util.DispatcherProvider
import me.mrSajal.ktornotesclient.common.util.Result
import me.mrSajal.ktornotesclient.notes.domain.NotesRepository
import okio.IOException

internal class NotesRepositoryImpl(
    private val notesApiService: NotesApiService,
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences
) : NotesRepository {


    override suspend fun getSingleNote(
        noteId: String
    ): Result<Notes> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val params = RemoteNotesParams(userData.userId, noteId)
                println("Repo response with  $params returns correct")
                val apiResponse = notesApiService.getNoteById(userData.token, params)
                println("Repo response with  $params  and response $apiResponse returns correct")

                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.note!!.toDomainNotes(),
                            )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getAllNotes(page: Int, pageSize: Int): Result<List<Notes>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = notesApiService.getAllNotes(userData.token, userData.userId,page, pageSize)

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.notes.map { it.toDomainNotes() })
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    HttpStatusCode.Forbidden -> {
                        Result.Success(data = emptyList())
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun deleteNote(
        noteId: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val params = RemoteNotesParams(
                    userId = userData.userId,
                    noteId = noteId
                )
                val apiResponse = notesApiService.deleteNote(
                    userToken = userData.token,
                    params
                )

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.success)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message ?: "Bad Request")
                    }

                    HttpStatusCode.Forbidden -> {
                        Result.Error(message = "Forbidden")
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message ?: "Unknown Error")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = exception.message ?: "An unexpected error occurred")
            }
        }
    }

    override suspend fun insertNote(
        noteTitle: String,
        noteContent: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val params = InsertNoteParams(
                    userId = userData.userId,
                    noteTitle, noteContent
                )
                val apiResponse = notesApiService.insertNote(
                    userToken = userData.token,
                    params
                )

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.success)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message ?: "Bad Request")
                    }

                    HttpStatusCode.Forbidden -> {
                        Result.Error(message = "Forbidden")
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message ?: "Unknown Error")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = exception.message ?: "An unexpected error occurred")
            }
        }
    }

    override suspend fun editNote(
        noteId: String,
        noteTitle: String,
        noteContent: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val params = EditNoteParams(
                    userId = userData.userId,
                    noteId = noteId,
                    noteTitle = noteTitle,
                    noteContent = noteContent
                )
                val apiResponse = notesApiService.editNote(
                    userToken = userData.token,
                    params
                )

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.success)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message ?: "Bad Request")
                    }

                    HttpStatusCode.Forbidden -> {
                        Result.Error(message = "Forbidden")
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message ?: "Unknown Error")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = exception.message ?: "An unexpected error occurred")
            }
        }
    }
}