package com.geo.ablemove.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenRoutes {
    @Serializable
    data object SplashRoute : ScreenRoutes

    @Serializable
    data object LoginRoute : ScreenRoutes

    @Serializable
    data object RegistrationRoute : ScreenRoutes

    @Serializable
    data object HomeRoute : ScreenRoutes

    @Serializable
    data object NoNetworkRoute : ScreenRoutes

    @Serializable
    data object SettingsRoute : ScreenRoutes

    @Serializable
    data object WeatherRoute : ScreenRoutes

    @Serializable
    data object MapRoute : ScreenRoutes

    @Serializable
    data object ActivitiesRoute : ScreenRoutes

    @Serializable
    data object ProfileRoute : ScreenRoutes
}
