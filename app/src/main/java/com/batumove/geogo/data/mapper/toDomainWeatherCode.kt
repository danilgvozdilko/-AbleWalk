package com.batumove.geogo.data.mapper

import com.batumove.geogo.domain.model.WeatherCode

internal fun Int.toDomainWeatherCode(): WeatherCode = WeatherCode.fromCode(this)