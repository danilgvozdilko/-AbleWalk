package com.batumove.geogo.domain.datastore

import com.batumove.geogo.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemePreferences {
    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}