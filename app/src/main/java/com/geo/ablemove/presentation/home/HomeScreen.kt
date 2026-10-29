package com.geo.ablemove.presentation.home

import android.util.Log
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
import com.geo.ablemove.navigation.ScreenRoutes
import com.geo.ablemove.presentation.weather.WeatherScreen
import com.geo.ablemove.ui.components.background.Background
import com.geo.ablemove.ui.components.MainBottomBar
import com.geo.ablemove.ui.components.MainTab
import com.geo.ablemove.ui.components.MainTopBar
import com.google.firebase.auth.FirebaseAuth

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
                        WeatherScreen(
                            bottomPadding = innerPadding.calculateBottomPadding()
                        )
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
