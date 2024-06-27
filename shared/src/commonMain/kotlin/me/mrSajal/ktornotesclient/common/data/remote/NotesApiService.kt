package me.mrSajal.ktornotesclient.common.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import me.mrSajal.ktornotesclient.common.data.model.EditNoteParams
import me.mrSajal.ktornotesclient.common.data.model.InsertNoteParams
import me.mrSajal.ktornotesclient.common.data.model.NotesApiResponse
import me.mrSajal.ktornotesclient.common.data.model.RemoteNotesParams
import me.mrSajal.ktornotesclient.common.domain.notes.Notes

internal class NotesApiService : KtorApi() {

    suspend fun insertNote(
        userToken: String,
        params: InsertNoteParams
    ): NotesApiResponse {
        val httpResponse = client.post {
            endPoint(path = "/note/create")
            setBody(params)
            setToken(userToken)
        }
        return NotesApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun getNoteById(
        userToken: String,
        notesParams: RemoteNotesParams
    ): NotesApiResponse {
        val httpResponse = client.get {
            endPoint(path = "/note/fetch")
            setBody(notesParams)
            setToken(userToken)
        }
        return NotesApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun deleteNote(
        userToken: String,
        notesParams: RemoteNotesParams
    ): NotesApiResponse {
        val httpResponse = client.delete {
            endPoint(path = "/note/delete")
            setBody(notesParams)
            setToken(userToken)
        }
        return NotesApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun getAllNotes(
        userToken: String,
        userId: String,
        page: Int,
        pageSize: Int
    ): NotesApiResponse {
        val httpResponse = client.get {
            endPoint(path = "/notes/all/$userId")  // Use path parameter
            parameter("page", page)
            parameter("limit", pageSize)
            setToken(userToken)
        }
        return NotesApiResponse(code = httpResponse.status, data = httpResponse.body())
    }


    suspend fun editNote(
        userToken: String,
        params: EditNoteParams
    ):NotesApiResponse{
        val httpResponse = client.post {
            endPoint(path = "/note/edit")
            setBody(params)
            setToken(userToken)
        }
        return NotesApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

}