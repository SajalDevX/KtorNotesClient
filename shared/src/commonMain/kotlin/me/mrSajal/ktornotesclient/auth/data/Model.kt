package me.mrSajal.ktornotesclient.auth.data

import kotlinx.serialization.Serializable
import me.mrSajal.ktornotesclient.common.domain.notes.NotesEntity

@Serializable
internal data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
internal data class SignInRequest(
    val email: String,
    val password: String
)

@Serializable
internal data class AuthResponse(
    val data: AuthResponseData? = null,
    val errorMessage: String? = null
)

@Serializable
internal data class AuthResponseData(
    val userId: String="",
    val name: String="",
    val imageUrl:String?= null,
    val token: String="",
    val notes: List<NotesEntity> = listOf()
)