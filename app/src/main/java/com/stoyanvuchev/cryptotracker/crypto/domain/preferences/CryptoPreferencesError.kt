package com.stoyanvuchev.cryptotracker.crypto.domain.preferences

import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.presentation.util.UIText

sealed class CryptoPreferencesError(uiText: UIText) : Error(uiText) {

    data object LastFetchedTimestampError : CryptoPreferencesError(
        uiText = UIText.BasicString("Couldn't load last fetched timestamp.")
    )

}