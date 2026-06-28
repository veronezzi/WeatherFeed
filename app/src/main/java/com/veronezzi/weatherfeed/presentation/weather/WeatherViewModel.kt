package com.veronezzi.weatherfeed.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veronezzi.weatherfeed.data.preferences.UserPreferencesRepository
import com.veronezzi.weatherfeed.domain.model.CurrentWeather
import com.veronezzi.weatherfeed.domain.usecase.GetCurrentWeatherUseCase
import com.veronezzi.weatherfeed.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentWeatherUseCase,
    private val prefs: UserPreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<CurrentWeather>>(UiState.Loading)
    val uiState: StateFlow<UiState<CurrentWeather>> = _uiState.asStateFlow()

    val isCelsius: StateFlow<Boolean> = prefs.isCelsius.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), true
    )

    val selectedCity: StateFlow<String> = prefs.selectedCity.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), "São Paulo"
    )

    init {
        viewModelScope.launch {
            prefs.selectedCity.collect { city ->
                loadWeather(city)
            }
        }
    }

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getCurrentWeather(city).fold(
                onSuccess = { _uiState.value = UiState.Success(it) },
                onFailure = { _uiState.value = UiState.Error(it.message ?: "Erro desconhecido") },
            )
        }
    }
}
