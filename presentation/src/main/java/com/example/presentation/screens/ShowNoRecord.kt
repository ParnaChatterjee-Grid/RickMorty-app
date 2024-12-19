package com.example.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ShowNoRecord(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(1f))
    {
        Text(
            text = "No Details Present For This Character",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}