package com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen

import androidx.compose.runtime.Immutable
import com.stoyanvuchev.cryptotracker.core.presentation.util.UIText

@Immutable
sealed interface CoinsListScreenUIAction {

    data object LoadCoins : CoinsListScreenUIAction

    data class ShowCoinDetails(
        val coinSymbol: String
    ) : CoinsListScreenUIAction

    data class Error(
        val error: UIText
    ) : CoinsListScreenUIAction

}