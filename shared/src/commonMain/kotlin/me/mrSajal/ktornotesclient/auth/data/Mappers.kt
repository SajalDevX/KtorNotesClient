package me.mrSajal.ktornotesclient.auth.data

import me.mrSajal.ktornotesclient.auth.domain.model.AuthResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData {
    return AuthResultData(userId,name, imageUrl, token,notes)
}