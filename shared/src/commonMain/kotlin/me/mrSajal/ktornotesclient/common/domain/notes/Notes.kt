package me.mrSajal.ktornotesclient.common.domain.notes

data class Notes(
    val noteId: String,
    val noteTitle: String = "",
    val noteContent: String = "",
    val createdAt: Long
)