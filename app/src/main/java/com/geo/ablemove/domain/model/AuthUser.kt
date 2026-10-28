package com.geo.ablemove.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    val id: String = "",
    val email: String? = null,
    val displayName: String? = "User",
    val photoUrl: String? = null
)