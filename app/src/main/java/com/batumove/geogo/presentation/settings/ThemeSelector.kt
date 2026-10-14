package com.batumove.geogo.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.batumove.geogo.ui.theme.ThemeMode

@Composable
fun ThemeSelector(
    selectedTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = ThemeMode.entries
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Тема оформления",
            style = MaterialTheme.typography.titleMedium
        )
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            options.forEachIndexed { index, mode ->
                SegmentedButton(
                    selected = selectedTheme == mode,
                    onClick = {
                        onThemeSelected(mode)
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    label = {
                        Text(
                            text = when (mode) {
                                ThemeMode.SYSTEM -> "Система"
                                ThemeMode.LIGHT -> "Светлая"
                                ThemeMode.DARK -> "Тёмная"
                            }
                        )
                    }
                )
            }
        }
    }
}
