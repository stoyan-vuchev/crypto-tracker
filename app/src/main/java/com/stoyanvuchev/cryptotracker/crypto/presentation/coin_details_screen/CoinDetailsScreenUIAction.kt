package com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen

import androidx.compose.runtime.Immutable
import com.stoyanvuchev.cryptotracker.core.presentation.util.UIText

@Immutable
sealed interface CoinDetailsScreenUIAction {

    data object NavigateUp : CoinDetailsScreenUIAction

    data class LoadCoinDetails(
        val coinSymbol: String
    ) : CoinDetailsScreenUIAction

    data class Error(
        val error: UIText
    ) : CoinDetailsScreenUIAction

}