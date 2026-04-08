package com.athallah.laundryku.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorIcon(isError: Boolean) {
    if (isError) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun ErrorText(isError: Boolean, text: String) {
    if (isError) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.error
        )
    }
}
