package com.geo.ablemove.domain.datastore

import com.geo.ablemove.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface UserDataPreferences {

    val userData: Flow<AuthUser?>

    val isLogined: Flow<Boolean>

    suspend fun getUserData(): AuthUser?

    suspend fun setUserData(user: AuthUser)

    suspend fun clearUserData()

    suspend fun getIsLogined(): Boolean

    suspend fun setIsLogined(isLogined: Boolean)
}