package com.geo.ablemove.domain.datastore

import com.geo.ablemove.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemePreferences {
    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}