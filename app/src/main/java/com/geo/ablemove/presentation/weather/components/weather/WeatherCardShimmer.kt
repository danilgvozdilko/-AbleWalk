package com.geo.ablemove.presentation.weather.components.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geo.ablemove.ui.components.cards.DefaultCard
import com.geo.ablemove.ui.components.shimmer.ShimmerPlaceholder

@Composable
fun WeatherCardShimmer() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(32.dp)
        )

        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(64.dp)
        )

        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(32.dp)
        )

        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(24.dp)
        )

        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(24.dp)
        )
        Spacer(Modifier.height(8.dp))
        DefaultCard(Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "HOURLY FORECAST",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) {
                        ShimmerPlaceholder(
                            modifier = Modifier
                                .width(64.dp)
                                .height(120.dp)
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        DefaultCard(Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(7) { index ->
                    ShimmerPlaceholder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    )
                    if (index < 6) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
    }
}
