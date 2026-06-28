package com.veronezzi.weatherfeed.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.designsystem.components.SectionLabel
import com.weather.designsystem.components.SettingsRow
import com.weather.designsystem.components.TemperatureToggle
import com.weather.designsystem.theme.WeatherTheme

private val BackgroundDark = Color(0xFF0D1230)
private val TextMuted = Color(0xFF4A5580)

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val isCelsius by viewModel.isCelsius.collectAsStateWithLifecycle()
    val spacing = WeatherTheme.spacing

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.md),
        ) {
            Spacer(Modifier.height(spacing.lg))
            SectionLabel(text = "PREFERÊNCIAS")
            Spacer(Modifier.height(spacing.xs))
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
            )
            Spacer(Modifier.height(spacing.md))

            SettingsRow(
                icon = "🌡️",
                title = "Unidade de temperatura",
                subtitle = "Celsius ou Fahrenheit",
                modifier = Modifier.fillMaxWidth(),
                trailing = {
                    TemperatureToggle(
                        isCelsius = isCelsius,
                        onToggle = viewModel::toggleUnit,
                    )
                },
            )
        }

        Text(
            text = "WeatherFeed\nVersão 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.xl),
        )
    }
}
