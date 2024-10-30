package com.stoyanvuchev.cryptotracker.core.domain.networking.util

import com.stoyanvuchev.cryptotracker.R
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError.NoInternet.uiText
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError.RequestTimeout.uiText
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError.Serialization.uiText
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError.ServerError.uiText
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError.TooManyRequests.uiText
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError.Unknown.uiText
import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.presentation.util.UIText

/**
 * Represents various types of network-related errors that can occur in the application,
 * with each error type associated with a specific, user-friendly message ([UIText])
 * for display in the UI.
 *
 * This sealed class extends the [Error] open class, making it compatible with error-handling
 * mechanisms expecting an [Error] type and allows for clear categorization of networking errors.
 */
sealed class NetworkError(override val uiText: UIText) : Error(uiText) {

    /**
     * Indicates a network request timeout, typically due to delayed server response.
     *
     * @property uiText A localized message to display when this error occurs,
     * linked to the string resource for request timeout errors.
     */
    data object RequestTimeout : NetworkError(
        uiText = UIText.StringResource(
            resId = R.string.network_error_request_timeout
        )
    )

    /**
     * Indicates that the user has made too many requests in a short period, triggering
     * a rate-limit error from the server.
     *
     * @property uiText A localized message to display for rate-limit errors, advising
     * the user to wait before retrying.
     */
    data object TooManyRequests : NetworkError(
        uiText = UIText.StringResource(
            resId = R.string.network_error_too_many_requests
        )
    )

    /**
     * Indicates that there is no internet connection available to complete the request.
     *
     * @property uiText A localized message informing the user of connectivity issues.
     */
    data object NoInternet : NetworkError(
        uiText = UIText.StringResource(
            resId = R.string.network_error_no_internet
        )
    )

    /**
     * Indicates that the server encountered an internal error and could not
     * process the request as expected.
     *
     * @property uiText A localized message to notify the user of a server-side error.
     */
    data object ServerError : NetworkError(
        uiText = UIText.StringResource(
            resId = R.string.network_error_server_error
        )
    )

    /**
     * Represents an error in data serialization or deserialization, often due to
     * an incompatibility or structure mismatch in the data.
     *
     * @property uiText A localized message for serialization issues encountered
     * during data processing, informing the user of an internal processing error.
     */
    data object Serialization : NetworkError(
        uiText = UIText.StringResource(
            resId = R.string.network_error_serialization
        )
    )

    /**
     * Represents a generic, unspecified network error that does not fall into
     * any of the specific categories above.
     *
     * @property uiText A localized message for unknown or unexpected network errors.
     */
    data object Unknown : NetworkError(
        uiText = UIText.StringResource(
            resId = R.string.network_error_unknown
        )
    )

}
