package com.geo.ablemove.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserDataPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserDataPreferences {

    private companion object {
        val USER_DATA = stringPreferencesKey("user_data")
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override val userData: Flow<AuthUser?> =
        dataStore.data.map { preferences ->
            preferences[USER_DATA]?.let { jsonString ->
                runCatching {
                    json.decodeFromString<AuthUser>(jsonString)
                }.getOrNull()
            }
        }

    override suspend fun getUserData(): AuthUser? {
        return userData.first()
    }

    override suspend fun setUserData(user: AuthUser) {
        dataStore.edit { preferences ->
            preferences[USER_DATA] = json.encodeToString(user)
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(USER_DATA)
        }
    }
}