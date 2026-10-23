package com.geo.ablemove.data.mapper

import com.geo.ablemove.domain.model.WeatherCode
import com.geo.ablemove.domain.model.WeatherVisualType

internal fun WeatherCode.toVisualType(): WeatherVisualType =
    when (this) {
        WeatherCode.CLEAR_SKY,
        WeatherCode.MAINLY_CLEAR -> WeatherVisualType.CLEAR

        WeatherCode.PARTLY_CLOUDY ->
            WeatherVisualType.PARTLY_CLOUDY

        WeatherCode.OVERCAST ->
            WeatherVisualType.OVERCAST

        WeatherCode.FOG,
        WeatherCode.DEPOSITING_RIME_FOG ->
            WeatherVisualType.FOG

        WeatherCode.LIGHT_DRIZZLE,
        WeatherCode.MODERATE_DRIZZLE,
        WeatherCode.DENSE_DRIZZLE,
        WeatherCode.LIGHT_FREEZING_DRIZZLE,
        WeatherCode.DENSE_FREEZING_DRIZZLE ->
            WeatherVisualType.DRIZZLE

        WeatherCode.SLIGHT_RAIN,
        WeatherCode.MODERATE_RAIN,
        WeatherCode.LIGHT_FREEZING_RAIN,
        WeatherCode.SLIGHT_RAIN_SHOWERS,
        WeatherCode.MODERATE_RAIN_SHOWERS ->
            WeatherVisualType.RAIN

        WeatherCode.HEAVY_RAIN,
        WeatherCode.HEAVY_FREEZING_RAIN,
        WeatherCode.VIOLENT_RAIN_SHOWERS ->
            WeatherVisualType.HEAVY_RAIN

        WeatherCode.SLIGHT_SNOWFALL,
        WeatherCode.MODERATE_SNOWFALL,
        WeatherCode.SNOW_GRAINS,
        WeatherCode.SLIGHT_SNOW_SHOWERS ->
            WeatherVisualType.SNOW

        WeatherCode.HEAVY_SNOWFALL,
        WeatherCode.HEAVY_SNOW_SHOWERS ->
            WeatherVisualType.HEAVY_SNOW

        WeatherCode.THUNDERSTORM,
        WeatherCode.HEAVY_THUNDERSTORM,
        WeatherCode.THUNDERSTORM_WITH_SLIGHT_HAIL,
        WeatherCode.THUNDERSTORM_WITH_HEAVY_HAIL ->
            WeatherVisualType.THUNDERSTORM

        WeatherCode.UNKNOWN ->
            WeatherVisualType.CLOUDY
    }
