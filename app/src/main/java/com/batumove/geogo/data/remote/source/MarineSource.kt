package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.dto.MarineDto

interface MarineSource {
    suspend fun getMarine(
        latitude: Double,
        longitude: Double,
    ): MarineDto
}