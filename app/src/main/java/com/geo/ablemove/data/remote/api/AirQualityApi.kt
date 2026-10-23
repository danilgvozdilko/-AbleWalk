package com.geo.ablemove.data.remote.api

import com.geo.ablemove.data.dto.AirQualityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApi {

    @GET("v1/air-quality")
    suspend fun getAirQuality(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "european_aqi,pm2_5,pm10",
        @Query("hourly") hourly: String =
            "european_aqi,pm2_5,pm10",
        @Query("forecast_days") days: Int = 7,
        @Query("timezone") timezone: String = "auto"
    ): AirQualityDto
}