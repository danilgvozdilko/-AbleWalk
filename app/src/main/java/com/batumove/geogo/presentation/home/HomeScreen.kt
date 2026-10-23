package com.batumove.geogo.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.batumove.geogo.navigation.ScreenRoutes
import com.batumove.geogo.presentation.weather.WeatherScreen
import com.batumove.geogo.ui.components.MainBottomBar
import com.batumove.geogo.ui.components.MainTab
import com.batumove.geogo.ui.components.MainTopBar
import com.batumove.geogo.ui.components.background.Background

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit
) {
    val navController = rememberNavController()

    val backStackEntry by navController
        .currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val selectedTab = when {
        currentDestination?.hasRoute<ScreenRoutes.MapRoute>() == true ->
            MainTab.MAP

        currentDestination?.hasRoute<ScreenRoutes.ActivitiesRoute>() == true ->
            MainTab.ACTIVITIES

        else -> MainTab.WEATHER
    }

    Background {
        Scaffold(
            topBar = {
                MainTopBar(
                    onProfileClick = onProfileClick
                )
            },

            bottomBar = {
                MainBottomBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->

                        val route = when (tab) {
                            MainTab.WEATHER -> ScreenRoutes.WeatherRoute
                            MainTab.MAP -> ScreenRoutes.MapRoute
                            MainTab.ACTIVITIES -> ScreenRoutes.ActivitiesRoute
                        }

                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoutes.WeatherRoute
                ) {
                    composable<ScreenRoutes.WeatherRoute> {
                        WeatherScreen()
                    }

                    composable<ScreenRoutes.ActivitiesRoute> {
//                        ActivitiesScreen()
                    }

                    composable<ScreenRoutes.MapRoute> {
//                        MapScreen()
                    }
                }
            }
        }
    }
}
