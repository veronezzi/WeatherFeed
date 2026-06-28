package com.veronezzi.weatherfeed.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veronezzi.weatherfeed.domain.model.CurrentWeather
import com.veronezzi.weatherfeed.presentation.common.UiState
import com.weather.designsystem.components.StatCard
import com.weather.designsystem.components.WeatherTopBar
import com.weather.designsystem.components.WeatherStat
import com.weather.designsystem.theme.WeatherTheme
import com.veronezzi.weatherfeed.data.remote.WeatherConditionMapper
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    onSearchClick: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isCelsius by viewModel.isCelsius.collectAsStateWithLifecycle()
    val selectedCity by viewModel.selectedCity.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF3B4BC8), Color(0xFF7B2FCA))
                )
            )
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                )
            }
            is UiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "⚠️",
                        fontSize = 48.sp,
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = state.message,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            is UiState.Success -> {
                WeatherContent(
                    weather = state.data,
                    isCelsius = isCelsius,
                    onSearchClick = onSearchClick,
                )
            }
        }
    }
}

@Composable
private fun WeatherContent(
    weather: CurrentWeather,
    isCelsius: Boolean,
    onSearchClick: () -> Unit,
) {
    val spacing = WeatherTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(spacing.lg))

        WeatherTopBar(
            locationText = "${weather.cityName}, ${weather.country}",
            onSearchClick = onSearchClick,
        )

        Spacer(Modifier.height(spacing.xl))

        Text(
            text = WeatherConditionMapper.toEmoji(weather.conditionCode),
            fontSize = 80.sp,
        )

        Spacer(Modifier.height(spacing.md))

        val temp = if (isCelsius) weather.tempC.roundToInt() else weather.tempF.roundToInt()
        Text(
            text = "$temp°",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White,
        )

        Spacer(Modifier.height(spacing.sm))

        Text(
            text = weather.conditionText,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.9f),
        )

        Spacer(Modifier.height(spacing.xs))

        val feelsLike = if (isCelsius) weather.feelsLikeC.roundToInt() else weather.feelsLikeF.roundToInt()
        val unit = if (isCelsius) "°C" else "°F"
        Text(
            text = "Sensação térmica $feelsLike$unit",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF64B5F6),
        )

        Spacer(Modifier.height(spacing.xl))

        StatCard(
            stats = listOf(
                WeatherStat(
                    icon = "🌡️",
                    label = "Sensação",
                    value = "$feelsLike°",
                ),
                WeatherStat(
                    icon = "💧",
                    label = "Umidade",
                    value = "${weather.humidity}%",
                ),
                WeatherStat(
                    icon = "💨",
                    label = "Vento",
                    value = "${weather.windKph.roundToInt()} km/h",
                ),
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
