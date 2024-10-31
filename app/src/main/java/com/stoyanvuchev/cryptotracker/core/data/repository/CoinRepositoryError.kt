package com.stoyanvuchev.cryptotracker.core.data.repository

import com.stoyanvuchev.cryptotracker.R
import com.stoyanvuchev.cryptotracker.core.data.repository.CoinRepositoryError.Unknown.uiText
import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.presentation.util.UIText

/**
 * Represents possible error types in the `CoinRepository`.
 *
 * This sealed class defines specific error cases that may occur while interacting
 * with the `CoinRepository`. It extends the [Error] open class, allowing it to be used
 * in error-handling mechanisms that expect an [Error] type. The errors are associated
 * with user-friendly messages ([UIText]) that can be displayed in the UI.
 */
sealed class CoinRepositoryError(override val uiText: UIText) : Error(uiText) {

    /**
     * Represents an unknown error.
     *
     * This error is used as a fallback for unspecified or unexpected issues
     * that may arise during operations in the `CoinRepository`.
     *
     * @property uiText A localized message to inform the user of an unknown
     * error encountered while interacting with the repository.
     */
    data object Unknown : CoinRepositoryError(
        uiText = UIText.StringResource(
            resId = R.string.coin_repository_error_unknown
        )
    )

}
