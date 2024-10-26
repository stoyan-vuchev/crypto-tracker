package com.stoyanvuchev.cryptotracker.core.domain.networking.util

import com.stoyanvuchev.cryptotracker.core.domain.util.Error

/**
 * Represents different types of network-related errors that may occur in the application.
 *
 * This enum implements the [Error] interface, making it compatible with any
 * error-handling mechanisms expecting an [Error] type.
 */
enum class NetworkError : Error {

    /**
     * Indicates that a network request has timed out.
     */
    REQUEST_TIMEOUT,

    /**
     * Indicates that too many requests have been made in a short period,
     * resulting in rate limiting.
     */
    TOO_MANY_REQUESTS,

    /**
     * Indicates that there is no internet connection available.
     */
    NO_INTERNET,

    /**
     * Indicates that the server encountered an error and could not complete the request.
     */
    SERVER_ERROR,

    /**
     * Indicates a failure in data serialization or deserialization.
     */
    SERIALIZATION,

    /**
     * Represents an unspecified or unknown network error.
     */
    UNKNOWN;

}