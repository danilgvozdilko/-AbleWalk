package com.batumove.geogo.domain.datastore

import kotlinx.coroutines.flow.Flow

interface NotificationPreferences {

    val notificationsEnabled: Flow<Boolean>

    suspend fun setNotificationsEnabled(enabled: Boolean)
}