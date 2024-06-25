package me.mrSajal.ktornotesclient.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import me.mrSajal.ktornotesclient.common.data.AndroidUserPreferences
import me.mrSajal.ktornotesclient.common.data.UserSettingsSerializer
import me.mrSajal.ktornotesclient.common.data.local.PREFERENCES_FILE_NAME
import me.mrSajal.ktornotesclient.common.data.local.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<UserPreferences> { AndroidUserPreferences(get()) }

    single {
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                androidContext().dataStoreFile(
                    fileName = PREFERENCES_FILE_NAME
                )
            }
        )
    }
}