package com.batumove.geogo.domain.usecase

import com.batumove.geogo.domain.model.Marine
import com.batumove.geogo.domain.source.MarineSource
import javax.inject.Inject

class MarineUseCase @Inject constructor(private val marineSource: MarineSource) {
    suspend fun getMarine(latitude: Double, longitude: Double): Result<Marine> =
        marineSource.getMarine(latitude, longitude)
}
