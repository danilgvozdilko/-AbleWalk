package com.geo.ablemove.data.mapper

import com.geo.ablemove.domain.model.WeatherCode

internal fun Int.toDomainWeatherCode(): WeatherCode = WeatherCode.fromCode(this)