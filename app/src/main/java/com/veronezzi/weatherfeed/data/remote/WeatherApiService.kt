package com.veronezzi.weatherfeed.data.remote

import com.veronezzi.weatherfeed.data.remote.dto.CurrentWeatherDto
import com.veronezzi.weatherfeed.data.remote.dto.ForecastResponseDto
import com.veronezzi.weatherfeed.data.remote.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("lang") lang: String = "pt",
    ): CurrentWeatherDto

    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 5,
        @Query("lang") lang: String = "pt",
    ): ForecastResponseDto

    @GET("v1/search.json")
    suspend fun searchCities(
        @Query("key") apiKey: String,
        @Query("q") query: String,
    ): List<SearchResultDto>
}
