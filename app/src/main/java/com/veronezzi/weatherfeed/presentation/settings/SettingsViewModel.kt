package com.veronezzi.weatherfeed.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veronezzi.weatherfeed.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: UserPreferencesRepository,
) : ViewModel() {

    val isCelsius: StateFlow<Boolean> = prefs.isCelsius.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), true
    )

    fun toggleUnit(isCelsius: Boolean) {
        viewModelScope.launch {
            prefs.setIsCelsius(isCelsius)
        }
    }
}
