package com.geo.ablemove.domain.model

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class DailyWeather(
    val time: List<String>,
    val maxTemperature: List<Double?>,
    val minTemperature: List<Double?>,
    val maxRainProbability: List<Int?>,
    val weatherCode: List<WeatherCode?>,
    val sunrise: List<String?>,
    val sunset: List<String?>,
    val maxUvIndex: List<Double?>
)

internal fun formatDay(
    date: String,
    isToday: Boolean
): String {
    if (isToday) return "Today"

    return LocalDate.parse(date)
        .dayOfWeek
        .getDisplayName(
            TextStyle.SHORT,
            Locale.ENGLISH
        )
}