package com.batumove.geogo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.batumove.geogo.presentation.home.HomeScreen
import com.batumove.geogo.presentation.profile.ProfileScreen
import com.batumove.geogo.presentation.settings.SettingsScreen
import com.batumove.geogo.ui.theme.ThemeMode

@Composable
fun NavigationHost(
    navController: NavHostController,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {

    NavHost(navController = navController, startDestination = ScreenRoutes.HomeRoute) {
        composable<ScreenRoutes.SplashRoute> {
//            val splashViewModel: SplashViewModel = hiltViewModel()
//            SplashScreen(
//                viewModel = splashViewModel,
//                onHomeScreen = {
//                    navController.navigate(ScreenRoutes.HomeScreen.route) {
//                        popUpTo(ScreenRoutes.SplashScreen.route) {
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                },
//                onNoNetworkScreen = {
//                    navController.navigate(ScreenRoutes.NoNetworkScreen.route) {
//                        popUpTo(ScreenRoutes.SplashScreen.route) {
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                }
//            )
        }

        composable<ScreenRoutes.HomeRoute> {
            HomeScreen(
                onProfileClick = {
                    navController.navigate(ScreenRoutes.ProfileRoute) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<ScreenRoutes.ProfileRoute> {
            ProfileScreen(
                onSettingsClick = {
                    navController.navigate(ScreenRoutes.SettingsRoute) {
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<ScreenRoutes.SettingsRoute> {
            SettingsScreen(
                themeMode = themeMode,
                onThemeChange = onThemeChange,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
