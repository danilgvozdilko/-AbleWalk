package com.geo.ablemove.presentation.weather.components.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.domain.model.Weather

@Composable
fun WeatherCard(locationName: String, unitFormatting: UnitFormatting, data: Weather) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CurrentWeatherCard(locationName, unitFormatting, data)
        HourlyWeatherCard(data.hourlyWeather)
        DailyWeatherCard(data.dailyWeather)
    }
}