package com.batumove.geogo.data.remote.source

import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.batumove.geogo.domain.source.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    val context: Context
) : LocationRepository {
    override suspend fun getLocationName(
        latitude: Double,
        longitude: Double,
    ): String? {
        if (!Geocoder.isPresent()) return null

        val geocoder = Geocoder(context, Locale.getDefault())

        val address = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { continuation ->
                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1,
                ) { addresses ->
                    if (continuation.isActive) {
                        continuation.resume(addresses.firstOrNull())
                    }
                }
            }
        } else {
            @Suppress("DEPRECATION")
            withContext(Dispatchers.IO) {
                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1,
                )?.firstOrNull()
            }
        }

        return address?.locality
            ?: address?.subAdminArea
            ?: address?.adminArea
            ?: address?.countryName
    }
}