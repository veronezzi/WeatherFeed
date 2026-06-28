package com.veronezzi.weatherfeed.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("location") val location: LocationDto,
    @SerializedName("current") val current: CurrentDto,
)

data class LocationDto(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
)

data class CurrentDto(
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("temp_f") val tempF: Double,
    @SerializedName("feelslike_c") val feelsLikeC: Double,
    @SerializedName("feelslike_f") val feelsLikeF: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("condition") val condition: ConditionDto,
)

data class ConditionDto(
    @SerializedName("text") val text: String,
    @SerializedName("code") val code: Int,
)
