package com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CoinDetailsScreenItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
) = Column(
    modifier = modifier.padding(horizontal = 32.dp)
) {

    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.secondary
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = value,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface
    )

}