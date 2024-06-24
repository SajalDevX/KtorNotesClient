package me.mrSajal.ktornotesclient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform