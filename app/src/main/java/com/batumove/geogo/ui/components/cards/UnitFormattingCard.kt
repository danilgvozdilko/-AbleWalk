package com.batumove.geogo.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.batumove.geogo.R
import com.batumove.geogo.domain.model.DistanceUnit
import com.batumove.geogo.domain.model.TemperatureUnit
import com.batumove.geogo.domain.model.UnitFormatting
import com.batumove.geogo.domain.model.WindSpeedUnit

@Composable
fun UnitFormattingCard(
    formatting: UnitFormatting,
    onTemperatureChange: (TemperatureUnit) -> Unit,
    onWindSpeedChange: (WindSpeedUnit) -> Unit,
    onDistanceChange: (DistanceUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(20.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
            .clip(shape)
            .background(colorScheme.surface)
            .border(
                width = 1.dp,
                color = colorScheme.outlineVariant,
                shape = shape
            )
    ) {
        SettingsRow(
            title = stringResource(R.string.temperature)
        ) {
            TemperatureSwitch(
                isCelsius = formatting.temperature == TemperatureUnit.CELSIUS,
                onChange = { isCelsius ->
                    onTemperatureChange(
                        if (isCelsius) TemperatureUnit.CELSIUS
                        else TemperatureUnit.FAHRENHEIT
                    )
                }
            )
        }

        SettingsDivider()

        SettingsRow(
            title = stringResource(R.string.wind_speed)
        ) {
            WindSpeedDropdown(
                selectedUnit = formatting.windSpeed,
                onUnitSelected = onWindSpeedChange
            )
        }

        SettingsDivider()

        SettingsRow(
            title = stringResource(R.string.distance_elevation)
        ) {
            DistanceDropdown(
                selectedUnit = formatting.distance,
                onUnitSelected = onDistanceChange
            )
        }
    }
}

@Composable
private fun SettingsRow(
    title: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(12.dp))

        content()
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
private fun TemperatureSwitch(
    isCelsius: Boolean,
    onChange: (Boolean) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(10.dp)

    Row(
        modifier = Modifier
            .clip(shape)
            .background(colorScheme.primaryContainer)
            .border(
                width = 1.dp,
                color = colorScheme.outlineVariant,
                shape = shape
            )
            .padding(3.dp)
    ) {
        TemperatureOption(
            text = "°C",
            selected = isCelsius,
            onClick = { onChange(true) }
        )

        TemperatureOption(
            text = "°F",
            selected = !isCelsius,
            onClick = { onChange(false) }
        )
    }
}

@Composable
private fun TemperatureOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (selected) colorScheme.primary
                else Color.Transparent
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) {
                colorScheme.onPrimary
            } else {
                colorScheme.onPrimaryContainer
            },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun WindSpeedDropdown(
    selectedUnit: WindSpeedUnit,
    onUnitSelected: (WindSpeedUnit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val colorScheme = MaterialTheme.colorScheme

    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = selectedUnit.displayName,
                color = colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = colorScheme.surfaceContainer
        ) {
            WindSpeedUnit.entries.forEach { unit ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = unit.displayName,
                            color = if (unit == selectedUnit) {
                                colorScheme.primary
                            } else {
                                colorScheme.onSurface
                            }
                        )
                    },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DistanceDropdown(
    selectedUnit: DistanceUnit,
    onUnitSelected: (DistanceUnit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val colorScheme = MaterialTheme.colorScheme

    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = selectedUnit.displayName,
                color = colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = colorScheme.surfaceContainer
        ) {
            DistanceUnit.entries.forEach { unit ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = unit.displayName,
                            color = if (unit == selectedUnit) {
                                colorScheme.primary
                            } else {
                                colorScheme.onSurface
                            }
                        )
                    },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}