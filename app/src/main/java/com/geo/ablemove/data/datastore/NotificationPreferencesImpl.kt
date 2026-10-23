package com.geo.ablemove.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.geo.ablemove.domain.datastore.NotificationPreferences
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificationPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : NotificationPreferences {

    private companion object {
        val NOTIFICATIONS_ENABLED_KEY =
            booleanPreferencesKey("notifications_enabled")
    }

    override val notificationsEnabled: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[NOTIFICATIONS_ENABLED_KEY] ?: false
        }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED_KEY] = enabled
        }
    }
}