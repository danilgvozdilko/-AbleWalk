package com.batumove.geogo.presentation.settings

import androidx.compose.runtime.Composable
import com.batumove.geogo.ui.components.background.Background
import com.batumove.geogo.ui.theme.ThemeMode

@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onBackClick: () -> Boolean
) {
    Background {
        ThemeSelector(
            selectedTheme = themeMode,
            onThemeSelected = onThemeChange
        )
    }
}
