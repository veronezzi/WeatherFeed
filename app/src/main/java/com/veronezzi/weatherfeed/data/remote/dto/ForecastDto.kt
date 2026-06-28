package com.veronezzi.weatherfeed.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    @SerializedName("location") val location: LocationDto,
    @SerializedName("forecast") val forecast: ForecastDto,
)

data class ForecastDto(
    @SerializedName("forecastday") val forecastday: List<ForecastDayDto>,
)

data class ForecastDayDto(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: DayDto,
)

data class DayDto(
    @SerializedName("maxtemp_c") val maxTempC: Double,
    @SerializedName("mintemp_c") val minTempC: Double,
    @SerializedName("maxtemp_f") val maxTempF: Double,
    @SerializedName("mintemp_f") val minTempF: Double,
    @SerializedName("condition") val condition: ConditionDto,
)
