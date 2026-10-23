package com.geo.ablemove.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geo.ablemove.presentation.home.HomeScreen
import com.geo.ablemove.presentation.login.LoginRoute
import com.geo.ablemove.presentation.profile.ProfileScreen
import com.geo.ablemove.presentation.registration.RegistrationScreen
import com.geo.ablemove.presentation.settings.SettingsScreen
import com.geo.ablemove.ui.theme.ThemeMode

@Composable
fun NavigationHost(
    navController: NavHostController,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {

    NavHost(navController = navController, startDestination = ScreenRoutes.LoginRoute) {
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

        composable<ScreenRoutes.LoginRoute> {
            LoginRoute({
                navController.navigate(ScreenRoutes.HomeRoute) {
                    popUpTo(ScreenRoutes.LoginRoute) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            })
        }

        composable<ScreenRoutes.RegistrationRoute> {
            RegistrationScreen()
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
