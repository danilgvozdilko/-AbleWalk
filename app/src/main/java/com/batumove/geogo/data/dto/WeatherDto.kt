package com.batumove.geogo.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentWeatherDto,
    val hourly: HourlyWeatherDto,
    val daily: DailyWeather
)

data class CurrentWeatherDto(
    val time: String,

    @SerializedName("temperature_2m")
    val temperature: Double?,

    @SerializedName("apparent_temperature")
    val feelsLike: Double?,

    @SerializedName("relative_humidity_2m")
    val humidity: Int?,

    val precipitation: Double?,

    @SerializedName("weather_code")
    val weatherCode: Int?,

    @SerializedName("wind_speed_10m")
    val windSpeed: Double?
)

data class HourlyWeatherDto(
    val time: List<String>,

    @SerializedName("temperature_2m")
    val temperature: List<Double?>,

    @SerializedName("apparent_temperature")
    val feelsLike: List<Double?>,

    @SerializedName("relative_humidity_2m")
    val humidity: List<Int?>,

    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int?>,

    val precipitation: List<Double?>,

    @SerializedName("weather_code")
    val weatherCode: List<Int?>,

    @SerializedName("wind_speed_10m")
    val windSpeed: List<Double?>,

    @SerializedName("wind_gusts_10m")
    val windGusts: List<Double?>,

    @SerializedName("uv_index")
    val uvIndex: List<Double?>,

    @SerializedName("is_day")
    val isDay: List<Int?>
)

data class DailyWeather(
    val time: List<String>,

    @SerializedName("temperature_2m_max")
    val maxTemperature: List<Double?>,

    @SerializedName("temperature_2m_min")
    val minTemperature: List<Double?>,

    @SerializedName("precipitation_probability_max")
    val maxRainProbability: List<Int?>,

    @SerializedName("weather_code")
    val weatherCode: List<Int?>,

    val sunrise: List<String?>,
    val sunset: List<String?>,

    @SerializedName("uv_index_max")
    val maxUvIndex: List<Double?>
)
