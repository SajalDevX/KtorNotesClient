package me.mrSajal.ktornotesclient.common.data.local

import me.mrSajal.ktornotesclient.auth.domain.model.AuthResultData
import kotlinx.serialization.Serializable
import me.mrSajal.ktornotesclient.common.domain.notes.NotesEntity

@Serializable
data class UserSettings(
    val userId: String="",
    val name: String="",
    val imageUrl:String?= null,
    val token: String="",
    val notes: List<NotesEntity> = listOf()
)


fun AuthResultData.toUserSettings(): UserSettings {
    return UserSettings(
        userId, name, imageUrl,token, notes
    )
}