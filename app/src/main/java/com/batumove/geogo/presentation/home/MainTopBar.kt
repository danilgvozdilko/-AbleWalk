package com.batumove.geogo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Weather",
                style = MaterialTheme.typography.titleLarge
            )
        },

        actions = {

            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Профиль",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}