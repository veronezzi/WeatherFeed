package com.veronezzi.weatherfeed.domain.usecase

import com.veronezzi.weatherfeed.domain.model.CurrentWeather
import com.veronezzi.weatherfeed.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    suspend operator fun invoke(location: String): Result<CurrentWeather> =
        repository.getCurrentWeather(location)
}
