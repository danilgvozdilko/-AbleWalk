package com.batumove.geogo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentWeatherDto,
    val hourly: HourlyWeatherDto,
    val daily: DailyWeatherDto
)

@Serializable
data class CurrentWeatherDto(
    val time: String,

    @SerialName("temperature_2m")
    val temperature: Double? = null,

    @SerialName("apparent_temperature")
    val feelsLike: Double? = null,

    @SerialName("relative_humidity_2m")
    val humidity: Int? = null,

    val precipitation: Double? = null,

    @SerialName("weather_code")
    val weatherCode: Int? = null,

    @SerialName("wind_speed_10m")
    val windSpeed: Double? = null
)

@Serializable
data class HourlyWeatherDto(
    val time: List<String>,

    @SerialName("temperature_2m")
    val temperature: List<Double?>,

    @SerialName("apparent_temperature")
    val feelsLike: List<Double?>,

    @SerialName("relative_humidity_2m")
    val humidity: List<Int?>,

    @SerialName("precipitation_probability")
    val precipitationProbability: List<Int?>,

    val precipitation: List<Double?>,

    @SerialName("weather_code")
    val weatherCode: List<Int?>,

    @SerialName("wind_speed_10m")
    val windSpeed: List<Double?>,

    @SerialName("wind_gusts_10m")
    val windGusts: List<Double?>,

    @SerialName("uv_index")
    val uvIndex: List<Double?>,

    @SerialName("is_day")
    val isDay: List<Int?>
)

@Serializable
data class DailyWeatherDto(
    val time: List<String>,

    @SerialName("temperature_2m_max")
    val maxTemperature: List<Double?>,

    @SerialName("temperature_2m_min")
    val minTemperature: List<Double?>,

    @SerialName("precipitation_probability_max")
    val maxRainProbability: List<Int?>,

    @SerialName("weather_code")
    val weatherCode: List<Int?>,

    val sunrise: List<String?>,
    val sunset: List<String?>,

    @SerialName("uv_index_max")
    val maxUvIndex: List<Double?>
)
