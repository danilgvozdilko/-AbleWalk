package com.batumove.geogo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AirQualityApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MarineApi