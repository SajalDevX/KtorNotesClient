package me.mrSajal.ktornotesclient.auth.domain.repository

import me.mrSajal.ktornotesclient.auth.domain.model.AuthResultData
import me.mrSajal.ktornotesclient.common.util.Result

internal interface AuthRepository {

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<AuthResultData>

    suspend fun signIn(email: String, password: String): Result<AuthResultData>
}