package me.mrSajal.ktornotesclient.auth.domain.model

import me.mrSajal.ktornotesclient.common.data.model.RemoteNotes

data class AuthResultData(
    val userId: String="",
    val name: String="",
    val imageUrl:String?= null,
    val token: String="",
    val notes: List<RemoteNotes> = listOf()
)