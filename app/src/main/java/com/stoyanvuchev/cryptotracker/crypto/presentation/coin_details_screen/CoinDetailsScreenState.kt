package com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen

import androidx.compose.runtime.Stable
import com.stoyanvuchev.cryptotracker.crypto.presentation.model.CoinUIModel

@Stable
data class CoinDetailsScreenState(
    val isLoading: Boolean = true,
    val coin: CoinUIModel? = null
)