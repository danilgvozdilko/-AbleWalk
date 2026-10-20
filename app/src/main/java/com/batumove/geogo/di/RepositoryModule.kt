package com.batumove.geogo.di

import com.batumove.geogo.data.datastore.NotificationPreferencesImpl
import com.batumove.geogo.data.datastore.ThemePreferencesImpl
import com.batumove.geogo.data.datastore.UnitFormattingPreferencesImpl
import com.batumove.geogo.data.remote.source.AirQualitySourceImpl
import com.batumove.geogo.data.remote.source.MarineSourceImpl
import com.batumove.geogo.data.remote.source.WeatherSourceImpl
import com.batumove.geogo.domain.datastore.NotificationPreferences
import com.batumove.geogo.domain.datastore.ThemePreferences
import com.batumove.geogo.domain.datastore.UnitFormattingPreferences
import com.batumove.geogo.domain.source.AirQualitySource
import com.batumove.geogo.domain.source.MarineSource
import com.batumove.geogo.domain.source.WeatherSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

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
}
