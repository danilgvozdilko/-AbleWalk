package com.geo.ablemove.data.remote.api

import com.geo.ablemove.data.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = CURRENT,
        @Query("hourly") hourly: String = HOURLY,
        @Query("daily") daily: String = DAILY,
        @Query("forecast_days") days: Int = 7,
        @Query("timezone") timezone: String = "auto",
        @Query("temperature_unit") temperatureUnit: String = "celsius"
    ): WeatherDto

    companion object {
        const val CURRENT =
            "temperature_2m,apparent_temperature," +
                    "relative_humidity_2m,precipitation," +
                    "weather_code,wind_speed_10m"

        const val HOURLY =
            "temperature_2m,apparent_temperature," +
                    "relative_humidity_2m,precipitation_probability," +
                    "weather_code,wind_speed_10m," +
                    "precipitation,uv_index,wind_gusts_10m,is_day"

        const val DAILY =
            "temperature_2m_max,temperature_2m_min," +
                    "precipitation_probability_max," +
                    "weather_code,sunrise,sunset,uv_index_max"
    }
}