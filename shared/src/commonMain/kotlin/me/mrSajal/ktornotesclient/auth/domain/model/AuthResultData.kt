package me.mrSajal.ktornotesclient.auth.domain.model

import me.mrSajal.ktornotesclient.common.domain.notes.NotesEntity

data class AuthResultData(
    val userId: String="",
    val name: String="",
    val imageUrl:String?= null,
    val token: String="",
    val notes: List<NotesEntity> = listOf()
)