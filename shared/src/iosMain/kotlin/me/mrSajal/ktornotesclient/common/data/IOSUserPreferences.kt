package me.mrSajal.ktornotesclient.common.data


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import me.mrSajal.ktornotesclient.common.data.local.UserPreferences
import me.mrSajal.ktornotesclient.common.data.local.UserSettings

internal class IOSUserPreferences (
    private val dataStore: DataStore<Preferences>
): UserPreferences {
    override suspend fun getUserData(): UserSettings {
        TODO("Not yet implemented")
    }

    override suspend fun setUserData(userSettings: UserSettings) {
        TODO("Not yet implemented")
    }

}


internal fun createDatastore(): DataStore<Preferences>? {
   return null
}