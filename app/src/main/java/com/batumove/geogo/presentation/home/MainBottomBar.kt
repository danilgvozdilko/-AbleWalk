package com.batumove.geogo.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainTab(
    val title: String,
    val icon: ImageVector
) {
    WEATHER(
        title = "Weather",
        icon = Icons.Default.WbSunny
    ),

    ACTIVITIES(
        title = "Activities",
        icon = Icons.Default.Accessibility
    ),

    MAP(
        title = "Explore",
        icon = Icons.Default.Map
    ),
}

@Composable
fun MainBottomBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        MainTab.entries.forEach { tab ->

            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = {
                    onTabSelected(tab)
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = {
                    Text(tab.title)
                },
                alwaysShowLabel = true
            )
        }
    }
}
