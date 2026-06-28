package com.veronezzi.weatherfeed.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veronezzi.weatherfeed.data.preferences.UserPreferencesRepository
import com.veronezzi.weatherfeed.domain.model.City
import com.veronezzi.weatherfeed.domain.usecase.SearchCitiesUseCase
import com.veronezzi.weatherfeed.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCities: SearchCitiesUseCase,
    private val prefs: UserPreferencesRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<List<City>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<City>>> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(value: String) {
        _query.value = value
        searchJob?.cancel()
        if (value.isBlank()) {
            _uiState.value = UiState.Success(emptyList())
            return
        }
        searchJob = viewModelScope.launch {
            delay(300)
            _uiState.value = UiState.Loading
            searchCities(value).fold(
                onSuccess = { _uiState.value = UiState.Success(it) },
                onFailure = { _uiState.value = UiState.Error(it.message ?: "Erro") },
            )
        }
    }

    fun selectCity(city: City) {
        viewModelScope.launch {
            prefs.setSelectedCity(city.name)
        }
    }
}
