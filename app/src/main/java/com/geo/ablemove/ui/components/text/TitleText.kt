package com.geo.ablemove.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geo.ablemove.ui.theme.LightSecondaryText

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        color = LightSecondaryText,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(
            horizontal = 24.dp,
            vertical = 16.dp
        )
    )
}