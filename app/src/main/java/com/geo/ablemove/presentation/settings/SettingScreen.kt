package com.geo.ablemove.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geo.ablemove.R
import com.geo.ablemove.ui.components.Background
import com.geo.ablemove.ui.components.SettingsTopBar
import com.geo.ablemove.ui.components.cards.NameCard
import com.geo.ablemove.ui.components.cards.NotificationSettingsCard
import com.geo.ablemove.ui.components.cards.UnitFormattingCard
import com.geo.ablemove.ui.components.text.TitleText
import com.geo.ablemove.ui.theme.AbleMoveDimens
import com.geo.ablemove.ui.theme.ThemeMode

@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onBackClick: () -> Boolean,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val user by viewModel.userState.collectAsStateWithLifecycle()

    val notificationsEnabled by viewModel.notificationsEnabled.collectAsStateWithLifecycle()


    val formatting by viewModel.unitFormatting.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SettingsTopBar({ onBackClick.invoke() }, modifier = Modifier)
        },
    ) { innerPadding ->


        Background {
            Column(
                Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                NameCard(
                    modifier = Modifier.padding(top = AbleMoveDimens.spacingLarge),
                    user = user
                )

                TitleText(stringResource(R.string.unit_formatting))

                UnitFormattingCard(
                    formatting = formatting,
                    onTemperatureChange = viewModel::updateTemperature,
                    onWindSpeedChange = viewModel::updateWindSpeed,
                    onDistanceChange = viewModel::updateDistance
                )

                TitleText(stringResource(R.string.appearance))
                ThemeSelector(
                    modifier = Modifier, selectedTheme = themeMode, onThemeSelected = onThemeChange
                )
                TitleText(stringResource(R.string.notifications))
                NotificationSettingsCard(
                    notificationsEnabled = notificationsEnabled,
                    onNotificationsChange = viewModel::updateNotificationsEnabled
                )
            }
        }
    }

}