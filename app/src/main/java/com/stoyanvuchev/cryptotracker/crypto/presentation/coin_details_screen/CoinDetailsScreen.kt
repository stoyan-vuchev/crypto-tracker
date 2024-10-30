package com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.cryptotracker.core.presentation.util.dateAndTimeStamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(
    screenState: CoinDetailsScreenState,
    onUIAction: (CoinDetailsScreenUIAction) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

            LargeTopAppBar(
                title = {

                    AnimatedContent(
                        targetState = screenState.coin,
                        label = ""
                    ) { coin ->

                        if (coin != null) {

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = coin.symbol
                            )

                        }

                    }

                },
                navigationIcon = {

                    IconButton(
                        onClick = { onUIAction(CoinDetailsScreenUIAction.NavigateUp) }
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Navigate Up."
                        )

                    }

                },
                scrollBehavior = scrollBehavior
            )

        }
    ) { safePadding ->

        AnimatedContent(
            targetState = screenState.coin,
            label = ""
        ) { coin ->

            if (coin != null) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    contentPadding = safePadding,
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {

                    item {
                        Spacer(modifier = Modifier.height(0.dp))
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Symbol",
                            value = coin.symbol
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 32.dp)
                        ) {

                            Text(
                                text = "Price Change (24h)",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            val isPriceChangeDecline by rememberUpdatedState(
                                coin.priceChange.startsWith("-")
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(
                                    text = coin.priceChange,
                                    color = if (isPriceChangeDecline) {
                                        MaterialTheme.colorScheme.error
                                    } else {
                                        MaterialTheme.colorScheme.primary
                                    },
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Icon(
                                    modifier = Modifier.size(24.dp),
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

                        }
                    }

                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 32.dp)
                        ) {

                            Text(
                                text = "Price Change Percent (24h)",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            val isPriceChangeDecline by rememberUpdatedState(
                                coin.priceChangePercent.startsWith("-")
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(
                                    text = coin.priceChangePercent + "%",
                                    color = if (isPriceChangeDecline) {
                                        MaterialTheme.colorScheme.error
                                    } else {
                                        MaterialTheme.colorScheme.primary
                                    },
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Icon(
                                    modifier = Modifier.size(24.dp),
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

                        }
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Weighted Average Price (24h)",
                            value = coin.weightedAvgPrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Previous Close Price",
                            value = coin.prevClosePrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Last Trade Price",
                            value = coin.lastPrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Last Trade Quantity",
                            value = coin.lastQty
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Highest Bid Price",
                            value = coin.bidPrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Highest Bid Quantity",
                            value = coin.bidQty
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Lowest Ask Price",
                            value = coin.askPrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Lowest Ask Quantity",
                            value = coin.askQty
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Opening Price (24h)",
                            value = coin.openPrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Highest Price (24h)",
                            value = coin.highPrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Lowest Price (24h)",
                            value = coin.lowPrice
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Total Volume (24h)",
                            value = coin.volume
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Total Quote Volume (24h)",
                            value = coin.quoteVolume
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Open Time",
                            value = coin.openTime.dateAndTimeStamp()
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Close Time",
                            value = coin.closeTime.dateAndTimeStamp()
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "First Trade ID",
                            value = coin.firstId.toString()
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Last Trade ID",
                            value = coin.lastId.toString()
                        )
                    }

                    item {
                        CoinDetailsScreenItem(
                            label = "Total Trades Count (24h)",
                            value = coin.count.toString()
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }

                }

            }

        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .padding(safePadding),
            visible = screenState.isLoading
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() }
            )

        }

    }

}