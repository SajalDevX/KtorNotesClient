package me.mrSajal.ktornotesclient.android

import android.app.Application
import me.mrSajal.ktornotesclient.android.di.appModule
import me.mrSajal.ktornotesclient.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesApplication)
            modules(appModule + getSharedModules() )
        }
    }
}