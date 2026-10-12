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
    fun provideRetrofit(
        json: Json,
        client: OkHttpClient,
    ): Retrofit = Retrofit.Builder().baseUrl("https://api.open-meteo.com/").client(client)
        .addConverterFactory(
            json.asConverterFactory(
                "application/json".toMediaType()
            )
        ).build()

    @Provides
    @Singleton
    fun provideAirApi(
        retrofit: Retrofit
    ): AirQualityApi = retrofit.create(AirQualityApi::class.java)

    @Provides
    @Singleton
    fun provideMarineApi(
        retrofit: Retrofit
    ): MarineApi = retrofit.create(MarineApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi = retrofit.create(WeatherApi::class.java)
}

