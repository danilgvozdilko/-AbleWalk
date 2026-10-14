package com.batumove.geogo.data.util

import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> safeApiCall(
    call: suspend () -> T
): Result<T> {
    return try {
        Result.success(call())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}