package com.batumove.geogo.domain.model

enum class WeatherCode(
    val code: Int,
    val description: String,
) {
    CLEAR_SKY(0, "Clear sky"),
    MAINLY_CLEAR(1, "Mainly clear"),
    PARTLY_CLOUDY(2, "Partly cloudy"),
    OVERCAST(3, "Overcast"),
    FOG(45, "Fog"),
    DEPOSITING_RIME_FOG(48, "Depositing rime fog"),
    LIGHT_DRIZZLE(51, "Light drizzle"),
    MODERATE_DRIZZLE(53, "Moderate drizzle"),
    DENSE_DRIZZLE(55, "Dense drizzle"),
    LIGHT_FREEZING_DRIZZLE(56, "Light freezing drizzle"),
    DENSE_FREEZING_DRIZZLE(57, "Dense freezing drizzle"),
    SLIGHT_RAIN(61, "Slight rain"),
    MODERATE_RAIN(63, "Moderate rain"),
    HEAVY_RAIN(65, "Heavy rain"),
    LIGHT_FREEZING_RAIN(66, "Light freezing rain"),
    HEAVY_FREEZING_RAIN(67, "Heavy freezing rain"),
    SLIGHT_SNOWFALL(71, "Slight snowfall"),
    MODERATE_SNOWFALL(73, "Moderate snowfall"),
    HEAVY_SNOWFALL(75, "Heavy snowfall"),
    SNOW_GRAINS(77, "Snow grains"),
    SLIGHT_RAIN_SHOWERS(80, "Slight rain showers"),
    MODERATE_RAIN_SHOWERS(81, "Moderate rain showers"),
    VIOLENT_RAIN_SHOWERS(82, "Violent rain showers"),
    SLIGHT_SNOW_SHOWERS(85, "Slight snow showers"),
    HEAVY_SNOW_SHOWERS(86, "Heavy snow showers"),
    THUNDERSTORM(95, "Thunderstorm"),
    THUNDERSTORM_WITH_SLIGHT_HAIL(96, "Thunderstorm with slight hail *"),
    HEAVY_THUNDERSTORM(97, "Heavy thunderstorm"),
    THUNDERSTORM_WITH_HEAVY_HAIL(99, "Thunderstorm with heavy hail *"),
    UNKNOWN(-1, "Unknown Weather");

    companion object {
        private val codes = entries.associateBy(WeatherCode::code)
        fun fromCode(code: Int): WeatherCode =
            codes[code] ?: UNKNOWN
    }
}