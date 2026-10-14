package com.batumove.geogo.di

import com.batumove.geogo.data.remote.api.AirQualityApi
import com.batumove.geogo.data.remote.api.MarineApi
import com.batumove.geogo.data.remote.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

private const val WEATHER_BASE_URL = "https://api.open-meteo.com/"
private const val AIR_QUALITY_BASE_URL = "https://air-quality-api.open-meteo.com/"
private const val MARINE_BASE_URL = "https://marine-api.open-meteo.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    @com.batumove.geogo.di.WeatherApi
    fun provideWeatherRetrofit(
        json: Json,
        client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()

    @Provides
    @Singleton
    @com.batumove.geogo.di.AirQualityApi
    fun provideAirQualityRetrofit(
        json: Json,
        client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(AIR_QUALITY_BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()

    @Provides
    @Singleton
    @com.batumove.geogo.di.MarineApi
    fun provideMarineRetrofit(
        json: Json,
        client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(MARINE_BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()


    @Provides
    @Singleton
    fun provideAirApi(
        @com.batumove.geogo.di.AirQualityApi retrofit: Retrofit
    ): AirQualityApi = retrofit.create(AirQualityApi::class.java)

    @Provides
    @Singleton
    fun provideMarineApi(
        @com.batumove.geogo.di.MarineApi retrofit: Retrofit
    ): MarineApi = retrofit.create(MarineApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApi(
        @com.batumove.geogo.di.WeatherApi retrofit: Retrofit
    ): WeatherApi = retrofit.create(WeatherApi::class.java)
}

