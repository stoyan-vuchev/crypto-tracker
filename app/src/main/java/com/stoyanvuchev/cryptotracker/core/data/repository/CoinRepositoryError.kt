package com.stoyanvuchev.cryptotracker.core.data.repository

import com.stoyanvuchev.cryptotracker.core.domain.util.Error

/**
 * Represents possible error types in the `CoinRepository`.
 *
 * This enum class defines specific error cases that may occur while interacting with the `CoinRepository`,
 * such as when coin data is unavailable or an unknown error is encountered.
 */
enum class CoinRepositoryError: Error {

    /**
     * Indicates that coin data is unavailable.
     *
     * This error may be returned when data retrieval fails, or there is no data to display.
     */
    COIN_DATA_UNAVAILABLE,

    /**
     * Represents an unknown error.
     *
     * This error is used as a fallback for unspecified or unexpected issues.
     */
    UNKNOWN;

}