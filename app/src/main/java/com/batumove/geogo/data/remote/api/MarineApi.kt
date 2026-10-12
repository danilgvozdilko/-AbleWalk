package com.batumove.geogo.data.remote.api

import com.batumove.geogo.data.dto.MarineDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarineApi {

    @GET("v1/marine")
    suspend fun getMarine(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "wave_height,sea_surface_temperature,wave_direction",
        @Query("hourly") hourly: String =
            "wave_height,sea_surface_temperature," +
                    "wave_direction,wave_period",
        @Query("forecast_days") days: Int = 7,
        @Query("timezone") timezone: String = "auto"
    ): MarineDto
}
