package com.veronezzi.weatherfeed.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veronezzi.weatherfeed.data.preferences.UserPreferencesRepository
import com.veronezzi.weatherfeed.domain.model.ForecastDay
import com.veronezzi.weatherfeed.domain.usecase.GetForecastUseCase
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
class ForecastViewModel @Inject constructor(
    private val getForecast: GetForecastUseCase,
    private val prefs: UserPreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ForecastDay>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ForecastDay>>> = _uiState.asStateFlow()

    val isCelsius: StateFlow<Boolean> = prefs.isCelsius.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), true
    )

    init {
        viewModelScope.launch {
            prefs.selectedCity.collect { city ->
                loadForecast(city)
            }
        }
    }

    private fun loadForecast(city: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getForecast(city).fold(
                onSuccess = { _uiState.value = UiState.Success(it) },
                onFailure = { _uiState.value = UiState.Error(it.message ?: "Erro desconhecido") },
            )
        }
    }
}
