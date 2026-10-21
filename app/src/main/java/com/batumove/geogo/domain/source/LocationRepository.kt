package com.batumove.geogo.domain.source

interface LocationRepository {
    suspend fun getLocationName(latitude: Double, longitude: Double): String?
}