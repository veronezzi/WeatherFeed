package com.veronezzi.weatherfeed.presentation.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veronezzi.weatherfeed.data.remote.WeatherConditionMapper
import com.veronezzi.weatherfeed.domain.model.ForecastDay
import com.veronezzi.weatherfeed.presentation.common.UiState
import com.weather.designsystem.components.ForecastDay as DsForecastDay
import com.weather.designsystem.components.ForecastRow
import com.weather.designsystem.components.SectionLabel
import com.weather.designsystem.theme.WeatherTheme
import kotlin.math.roundToInt

private val BackgroundDark = Color(0xFF0D1230)

@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isCelsius by viewModel.isCelsius.collectAsStateWithLifecycle()
    val spacing = WeatherTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = spacing.md),
    ) {
        Spacer(Modifier.height(spacing.lg))
        SectionLabel(text = "PRÓXIMOS DIAS")
        Spacer(Modifier.height(spacing.xs))
        Text(
            text = "Previsão de 5 dias",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
        )
        Spacer(Modifier.height(spacing.md))

        when (val state = uiState) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF5B9EF0))
                }
            }
            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = Color.White)
                }
            }
            is UiState.Success -> {
                LazyColumn {
                    items(state.data) { day ->
                        ForecastRow(
                            day = day.toDsModel(isCelsius),
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Spacer(Modifier.height(spacing.sm))
                    }
                }
            }
        }
    }
}

private fun ForecastDay.toDsModel(isCelsius: Boolean): DsForecastDay {
    val max = if (isCelsius) maxTempC.roundToInt() else maxTempF.roundToInt()
    val min = if (isCelsius) minTempC.roundToInt() else minTempF.roundToInt()
    return DsForecastDay(
        dayName = dayName,
        date = date,
        conditionIcon = WeatherConditionMapper.toEmoji(conditionCode),
        conditionLabel = conditionText,
        tempMax = "$max°",
        tempMin = "$min°",
    )
}
