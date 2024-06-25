package me.mrSajal.ktornotesclient.common.domain.notes

import kotlinx.serialization.Serializable


@Serializable
data class NotesEntity(
    val noteId: String,
    val noteTitle: String = "",
    val noteContent: String = "",
    val createdAt: Long
)