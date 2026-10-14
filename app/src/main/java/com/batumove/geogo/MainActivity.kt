package com.batumove.geogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.batumove.geogo.navigation.NavigationHost
import com.batumove.geogo.presentation.settings.ThemeViewModel
import com.batumove.geogo.ui.theme.GeoGOTheme
import com.batumove.geogo.ui.theme.ThemeMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val themeMode by themeViewModel.themeMode
                .collectAsStateWithLifecycle()

            val darkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }
            GeoGOTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                NavigationHost(
                    navController = navController,
                    themeMode = themeMode,
                    onThemeChange = themeViewModel::setThemeMode
                )
            }
        }
    }
}
