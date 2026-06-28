package com.veronezzi.weatherfeed.data.repository

import com.veronezzi.weatherfeed.BuildConfig
import com.veronezzi.weatherfeed.data.remote.WeatherApiService
import com.veronezzi.weatherfeed.data.remote.WeatherConditionMapper
import com.veronezzi.weatherfeed.domain.model.City
import com.veronezzi.weatherfeed.domain.model.CurrentWeather
import com.veronezzi.weatherfeed.domain.model.ForecastDay
import com.veronezzi.weatherfeed.domain.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService,
) : WeatherRepository {

    override suspend fun getCurrentWeather(location: String): Result<CurrentWeather> = runCatching {
        val dto = api.getCurrentWeather(BuildConfig.WEATHER_API_KEY, location)
        CurrentWeather(
            cityName = dto.location.name,
            country = dto.location.country,
            tempC = dto.current.tempC,
            tempF = dto.current.tempF,
            feelsLikeC = dto.current.feelsLikeC,
            feelsLikeF = dto.current.feelsLikeF,
            humidity = dto.current.humidity,
            windKph = dto.current.windKph,
            conditionText = dto.current.condition.text,
            conditionCode = dto.current.condition.code,
        )
    }

    override suspend fun getForecast(location: String, days: Int): Result<List<ForecastDay>> = runCatching {
        val dto = api.getForecast(BuildConfig.WEATHER_API_KEY, location, days)
        val inputFmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayFmt = SimpleDateFormat("EEE", Locale("pt", "BR"))
        dto.forecast.forecastday.map { dayDto ->
            val date = inputFmt.parse(dayDto.date)
            val cal = Calendar.getInstance().also { c -> date?.let { c.time = it } }
            val isToday = isSameDay(cal)
            val dayName = if (isToday) "Hoje" else dayFmt.format(cal.time).replaceFirstChar { it.uppercase() }
            val formattedDate = "${cal.get(Calendar.DAY_OF_MONTH)} ${monthAbbr(cal.get(Calendar.MONTH))}"
            ForecastDay(
                date = formattedDate,
                dayName = dayName,
                maxTempC = dayDto.day.maxTempC,
                minTempC = dayDto.day.minTempC,
                maxTempF = dayDto.day.maxTempF,
                minTempF = dayDto.day.minTempF,
                conditionText = dayDto.day.condition.text,
                conditionCode = dayDto.day.condition.code,
            )
        }
    }

    override suspend fun searchCities(query: String): Result<List<City>> = runCatching {
        api.searchCities(BuildConfig.WEATHER_API_KEY, query).map { dto ->
            City(id = dto.id, name = dto.name, country = dto.country, region = dto.region)
        }
    }

    private fun isSameDay(cal: Calendar): Boolean {
        val today = Calendar.getInstance()
        return cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
    }

    private fun monthAbbr(month: Int): String = when (month) {
        0 -> "Jan"; 1 -> "Fev"; 2 -> "Mar"; 3 -> "Abr"
        4 -> "Mai"; 5 -> "Jun"; 6 -> "Jul"; 7 -> "Ago"
        8 -> "Set"; 9 -> "Out"; 10 -> "Nov"; else -> "Dez"
    }
}
