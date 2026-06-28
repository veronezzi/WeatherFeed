package com.veronezzi.weatherfeed.domain.model

data class CurrentWeather(
    val cityName: String,
    val country: String,
    val tempC: Double,
    val tempF: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val humidity: Int,
    val windKph: Double,
    val conditionText: String,
    val conditionCode: Int,
)
