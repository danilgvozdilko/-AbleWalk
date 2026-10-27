package com.geo.ablemove.presentation.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Waves
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geo.ablemove.R
import com.geo.ablemove.domain.model.Marine
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.ui.components.cards.DefaultCard
import java.util.Locale

@Composable
fun MarineCard(
    marine: Marine,
    unitFormatting: UnitFormatting,
    modifier: Modifier = Modifier
) {
    val current = marine.current

    val temperature = current?.seaTemperature
    val waveHeight = current?.waveHeight
    val waveDirection = current?.waveDirection

    val accentColor = MaterialTheme.colorScheme.primary

    val seaStatus = when {
        waveHeight == null -> stringResource(R.string.unknown)
        waveHeight < 0.5 -> stringResource(R.string.calm)
        waveHeight < 1.25 -> stringResource(R.string.slight)
        waveHeight < 2.5 -> stringResource(R.string.moderate)
        waveHeight < 4.0 -> stringResource(R.string.rough)
        else -> stringResource(R.string.very_rough)
    }

    DefaultCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Waves,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(19.dp)
                    )

                    Text(
                        text = stringResource(R.string.sea_conditions),
                        color = accentColor,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Text(
                    text = seaStatus,
                    color = accentColor,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = temperature?.let {
                        String.format(Locale.US, "%.1f", it)
                    } ?: "—",
                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = unitFormatting.temperature.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Text(
                text = stringResource(R.string.sea_surface_temperature),
                style = MaterialTheme.typography.labelMedium
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.wave_height),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = waveHeight?.let {
                            "${String.format(Locale.US, "%.1f", it)} m"
                        } ?: "—",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(R.string.direction),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = waveDirection?.let {
                            "${it.toInt()}°"
                        } ?: "—",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
