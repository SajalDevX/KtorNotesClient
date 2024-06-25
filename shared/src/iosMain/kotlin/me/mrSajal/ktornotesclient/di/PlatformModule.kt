package me.mrSajal.ktornotesclient.di

import me.mrSajal.ktornotesclient.common.data.IOSUserPreferences
import me.mrSajal.ktornotesclient.common.data.createDatastore
import me.mrSajal.ktornotesclient.common.data.local.UserPreferences
import org.koin.dsl.module

actual val platformModule = module {
    single<UserPreferences> { IOSUserPreferences(get()) }

    single {
        createDatastore()
    }
}