package com.veronezzi.weatherfeed.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.veronezzi.weatherfeed.presentation.forecast.ForecastScreen
import com.veronezzi.weatherfeed.presentation.search.SearchScreen
import com.veronezzi.weatherfeed.presentation.settings.SettingsScreen
import com.veronezzi.weatherfeed.presentation.weather.WeatherScreen

sealed class Screen(val route: String, val navIndex: Int) {
    data object Weather : Screen("weather", 0)
    data object Forecast : Screen("forecast", 1)
    data object Search : Screen("search", 2)
    data object Settings : Screen("settings", 3)
}

private val screens = listOf(Screen.Weather, Screen.Forecast, Screen.Search, Screen.Settings)

fun NavHostController.navigateToTab(screen: Screen) {
    navigate(screen.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun currentNavIndex(navController: NavHostController): Int {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    return screens.find { it.route == currentRoute }?.navIndex ?: 0
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Weather.route,
        modifier = modifier,
    ) {
        composable(Screen.Weather.route) {
            WeatherScreen(
                onSearchClick = { navController.navigateToTab(Screen.Search) },
            )
        }
        composable(Screen.Forecast.route) {
            ForecastScreen()
        }
        composable(Screen.Search.route) {
            SearchScreen(
                onCitySelected = { navController.navigateToTab(Screen.Weather) },
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
