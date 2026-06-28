package com.veronezzi.weatherfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.veronezzi.weatherfeed.presentation.navigation.AppNavHost
import com.veronezzi.weatherfeed.presentation.navigation.Screen
import com.veronezzi.weatherfeed.presentation.navigation.currentNavIndex
import com.veronezzi.weatherfeed.presentation.navigation.navigateToTab
import com.weather.designsystem.components.WeatherBottomNav
import com.weather.designsystem.components.defaultNavItems
import com.weather.designsystem.theme.WeatherFeedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherFeedTheme {
                val navController = rememberNavController()
                val selectedIndex = currentNavIndex(navController)

                val screens = listOf(
                    Screen.Weather,
                    Screen.Forecast,
                    Screen.Search,
                    Screen.Settings,
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 80.dp),
                    )

                    WeatherBottomNav(
                        items = defaultNavItems,
                        selectedIndex = selectedIndex,
                        onItemSelected = { index ->
                            navController.navigateToTab(screens[index])
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .windowInsetsPadding(WindowInsets.navigationBars),
                    )
                }
            }
        }
    }
}
