package com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.cryptotracker.crypto.presentation.model.CoinUIModel
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape

@Composable
fun CoinsListScreenItem(
    modifier: Modifier = Modifier,
    coin: CoinUIModel,
    onUIAction: (CoinsListScreenUIAction) -> Unit
) {

    val isPriceChangeDecline by rememberUpdatedState(
        coin.priceChangePercent.startsWith("-")
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
        ),
        onClick = { onUIAction(CoinsListScreenUIAction.ShowCoinDetails(coin.symbol)) },
        shape = SquircleShape(radius = 12.dp, cornerSmoothing = CornerSmoothing.Small)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalAlignment = Alignment.Top
        ) {

            Text(
                text = coin.symbol,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = coin.priceChangePercent + "%",
                color = if (isPriceChangeDecline) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                },
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = if (isPriceChangeDecline) {
                    Icons.Rounded.KeyboardArrowDown
                } else {
                    Icons.Rounded.KeyboardArrowUp
                },
                contentDescription = null,
                tint = if (isPriceChangeDecline) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {

            Text(
                text = "bid / ask:  ${coin.bidPrice} / ${coin.askPrice}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .67f),
                style = MaterialTheme.typography.bodyMedium
            )

        }

    }

}