package com.batumove.geogo.presentation.settings

import androidx.compose.runtime.Composable
import com.batumove.geogo.ui.theme.ThemeMode

@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onBackClick: () -> Boolean
) {
    ThemeSelector(
        selectedTheme = themeMode,
        onThemeSelected = onThemeChange
    )
}
