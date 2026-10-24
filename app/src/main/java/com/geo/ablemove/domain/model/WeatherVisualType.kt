package com.geo.ablemove.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.geo.ablemove.R

internal enum class WeatherVisualType {
    CLEAR,
    PARTLY_CLOUDY,
    CLOUDY,
    OVERCAST,
    FOG,
    DRIZZLE,
    RAIN,
    HEAVY_RAIN,
    SNOW,
    HEAVY_SNOW,
    THUNDERSTORM,
}

@DrawableRes
internal fun WeatherVisualType.toIconRes(): Int =
    when (this) {
        WeatherVisualType.CLEAR -> R.drawable.ic_weather_clear

        WeatherVisualType.PARTLY_CLOUDY -> R.drawable.ic_weather_partly_cloudy

        WeatherVisualType.FOG -> R.drawable.ic_weather_fog

        WeatherVisualType.RAIN,
        WeatherVisualType.DRIZZLE,
        WeatherVisualType.HEAVY_RAIN -> R.drawable.ic_weather_rain

        WeatherVisualType.SNOW,
        WeatherVisualType.HEAVY_SNOW -> R.drawable.ic_weather_snow

        WeatherVisualType.THUNDERSTORM -> R.drawable.ic_weather_thunderstorm

        WeatherVisualType.CLOUDY,
        WeatherVisualType.OVERCAST -> R.drawable.ic_weather_cloudy
    }

internal data class WeatherGradient(
    val colors: List<Color>,
)

internal fun WeatherVisualType.toGradient(
    isDay: Boolean,
): WeatherGradient {
    if (!isDay) {
        return WeatherGradient(
            colors = listOf(
                Color(0xFF101B33),
                Color(0xFF253B58),
                Color(0xFF46546A),
            )
        )
    }

    return when (this) {
        WeatherVisualType.CLEAR ->
            WeatherGradient(
                listOf(
                    Color(0xFF28649A),
                    Color(0xFF579AC9),
                    Color(0xFFA7CFE3),
                )
            )

        WeatherVisualType.PARTLY_CLOUDY ->
            WeatherGradient(
                listOf(
                    Color(0xFF456F9B),
                    Color(0xFF86ABC7),
                    Color(0xFFD4DCE0),
                )
            )

        WeatherVisualType.CLOUDY,
        WeatherVisualType.OVERCAST ->
            WeatherGradient(
                listOf(
                    Color(0xFF586D7E),
                    Color(0xFF8999A5),
                    Color(0xFFB8C0C5),
                )
            )

        WeatherVisualType.FOG ->
            WeatherGradient(
                listOf(
                    Color(0xFF8A9CA5),
                    Color(0xFFBBC5C9),
                    Color(0xFFDCE0DF),
                )
            )

        WeatherVisualType.DRIZZLE,
        WeatherVisualType.RAIN ->
            WeatherGradient(
                listOf(
                    Color(0xFF354C62),
                    Color(0xFF60798A),
                    Color(0xFF94A8B3),
                )
            )

        WeatherVisualType.HEAVY_RAIN ->
            WeatherGradient(
                listOf(
                    Color(0xFF202F40),
                    Color(0xFF41596A),
                    Color(0xFF738894),
                )
            )

        WeatherVisualType.SNOW,
        WeatherVisualType.HEAVY_SNOW ->
            WeatherGradient(
                listOf(
                    Color(0xFF738FA8),
                    Color(0xFFAABECB),
                    Color(0xFFDCE5E9),
                )
            )

        WeatherVisualType.THUNDERSTORM ->
            WeatherGradient(
                listOf(
                    Color(0xFF182333),
                    Color(0xFF303E50),
                    Color(0xFF566578),
                )
            )
    }
}