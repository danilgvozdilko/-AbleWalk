package com.geo.ablemove.di

import com.geo.ablemove.data.datastore.NotificationPreferencesImpl
import com.geo.ablemove.data.datastore.ThemePreferencesImpl
import com.geo.ablemove.data.datastore.UnitFormattingPreferencesImpl
import com.geo.ablemove.data.datastore.UserDataPreferencesImpl
import com.geo.ablemove.data.remote.source.AirQualitySourceImpl
import com.geo.ablemove.data.remote.source.MarineSourceImpl
import com.geo.ablemove.data.remote.source.WeatherSourceImpl
import com.geo.ablemove.domain.datastore.NotificationPreferences
import com.geo.ablemove.domain.datastore.ThemePreferences
import com.geo.ablemove.domain.datastore.UnitFormattingPreferences
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.source.AirQualitySource
import com.geo.ablemove.domain.source.MarineSource
import com.geo.ablemove.domain.source.WeatherSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    abstract fun bindWeatherSource(
        weatherSourceImpl: WeatherSourceImpl
    ): WeatherSource

    @Binds
    abstract fun bindAirQualitySource(
        airQualitySourceImpl: AirQualitySourceImpl
    ): AirQualitySource

    @Binds
    abstract fun bindMarineSource(
        marineSourceImpl: MarineSourceImpl
    ): MarineSource

    @Binds
    @Singleton
    abstract fun bindThemePreferences(
        implementation: ThemePreferencesImpl
    ): ThemePreferences

    @Binds
    @Singleton
    abstract fun bindUnitFormattingPreferences(
        impl: UnitFormattingPreferencesImpl
    ): UnitFormattingPreferences

    @Binds
    @Singleton
    abstract fun bindNotificationPreferences(
        impl: NotificationPreferencesImpl
    ): NotificationPreferences

    @Binds
    @Singleton
    abstract fun bindUserDataPreferences(
        impl: UserDataPreferencesImpl
    ): UserDataPreferences
}
