package com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen

import androidx.compose.runtime.Stable
import com.stoyanvuchev.cryptotracker.crypto.presentation.model.CoinUIModel

@Stable
data class CoinsListScreenState(
    val isLoading: Boolean = true,
    val currentPage: Int = 1,
    val pageSize: Int = 20,
    val coins: List<CoinUIModel> = emptyList(),
)