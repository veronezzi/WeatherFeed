package com.veronezzi.weatherfeed.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.veronezzi.weatherfeed.presentation.common.UiState
import com.weather.designsystem.components.CityRow
import com.weather.designsystem.components.WeatherSearchBar
import com.weather.designsystem.theme.WeatherTheme

private val BackgroundDark = Color(0xFF0D1230)
private val TextSecondary = Color(0xFF8892B0)

@Composable
fun SearchScreen(
    onCitySelected: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = WeatherTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
    ) {
        Column(modifier = Modifier.padding(horizontal = spacing.md)) {
            Spacer(Modifier.height(spacing.lg))
            Text(
                text = "Buscar",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
            )
            Spacer(Modifier.height(spacing.md))
            WeatherSearchBar(
                value = query,
                onValueChange = viewModel::onQueryChange,
                placeholder = "Buscar cidade ou país...",
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(spacing.md))
        }

        when (val state = uiState) {
            is UiState.Loading -> {
                Text(
                    text = "Buscando...",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = spacing.md),
                )
            }
            is UiState.Error -> {
                Text(
                    text = state.message,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = spacing.md),
                )
            }
            is UiState.Success -> {
                val cities = state.data
                if (cities.isNotEmpty()) {
                    Text(
                        text = "${cities.size} RESULTADOS",
                        color = TextSecondary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = spacing.md),
                    )
                    Spacer(Modifier.height(spacing.sm))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = spacing.md),
                    ) {
                        items(cities) { city ->
                            CityRow(
                                cityName = city.name,
                                country = city.country,
                                onClick = {
                                    viewModel.selectCity(city)
                                    onCitySelected()
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(Modifier.height(spacing.sm))
                        }
                        item { Spacer(Modifier.height(spacing.sm)) }
                    }
                }
            }
        }
    }
}
