package com.batumove.geogo.domain.source

import com.batumove.geogo.domain.model.Marine

interface MarineSource {
    suspend fun getMarine(
        latitude: Double,
        longitude: Double,
    ): Result<Marine>
}