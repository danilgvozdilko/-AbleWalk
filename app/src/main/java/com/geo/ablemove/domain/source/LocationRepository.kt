package com.geo.ablemove.domain.source

interface LocationRepository {
    suspend fun getLocationName(latitude: Double, longitude: Double): String?
}