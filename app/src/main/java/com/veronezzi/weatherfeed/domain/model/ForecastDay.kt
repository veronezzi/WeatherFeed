package com.veronezzi.weatherfeed.domain.model

data class ForecastDay(
    val date: String,
    val dayName: String,
    val maxTempC: Double,
    val minTempC: Double,
    val maxTempF: Double,
    val minTempF: Double,
    val conditionText: String,
    val conditionCode: Int,
)
