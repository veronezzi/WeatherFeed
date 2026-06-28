package com.veronezzi.weatherfeed.domain.usecase

import com.veronezzi.weatherfeed.domain.model.ForecastDay
import com.veronezzi.weatherfeed.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    suspend operator fun invoke(location: String): Result<List<ForecastDay>> =
        repository.getForecast(location)
}
