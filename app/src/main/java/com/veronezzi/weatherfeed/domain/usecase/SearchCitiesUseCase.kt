package com.veronezzi.weatherfeed.domain.usecase

import com.veronezzi.weatherfeed.domain.model.City
import com.veronezzi.weatherfeed.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    suspend operator fun invoke(query: String): Result<List<City>> =
        repository.searchCities(query)
}
