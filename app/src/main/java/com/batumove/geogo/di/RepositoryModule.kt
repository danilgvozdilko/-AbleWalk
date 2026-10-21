package com.batumove.geogo.di

import android.content.Context
import com.batumove.geogo.data.remote.source.LocationRepositoryImpl
import com.batumove.geogo.domain.source.LocationRepository
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
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context
    ): LocationRepository {
        return LocationRepositoryImpl(context)
    }
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
