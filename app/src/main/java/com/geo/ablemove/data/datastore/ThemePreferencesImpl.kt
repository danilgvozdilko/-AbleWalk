package com.geo.ablemove.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.geo.ablemove.domain.datastore.ThemePreferences
import com.geo.ablemove.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemePreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ThemePreferences {
    private companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    override val themeMode: Flow<ThemeMode> =
        dataStore.data.map { preferences ->
            val savedMode = preferences[THEME_MODE_KEY]
            ThemeMode.entries.firstOrNull {
                it.name == savedMode
            } ?: ThemeMode.SYSTEM
        }

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode.name
        }
    }
}