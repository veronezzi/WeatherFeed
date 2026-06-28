package com.veronezzi.weatherfeed.domain.repository

import com.veronezzi.weatherfeed.domain.model.City
import com.veronezzi.weatherfeed.domain.model.CurrentWeather
import com.veronezzi.weatherfeed.domain.model.ForecastDay

interface WeatherRepository {
    suspend fun getCurrentWeather(location: String): Result<CurrentWeather>
    suspend fun getForecast(location: String, days: Int = 5): Result<List<ForecastDay>>
    suspend fun searchCities(query: String): Result<List<City>>
}
