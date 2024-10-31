package com.stoyanvuchev.cryptotracker.crypto.domain.preferences

import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.domain.util.Result

/**
 * Interface for managing cryptocurrency-related preferences, such as storing and retrieving
 * timestamps for data fetching operations.
 *
 * Implementations of this interface provide a way to persist and retrieve preferences that are
 * used by the application to track data fetch timing, enabling efficient data caching and refresh logic.
 */
interface CryptoPreferences {

    /**
     * Retrieves the timestamp of the last successful data fetch operation.
     *
     * This timestamp can be used to determine if locally cached data is up-to-date or needs to
     * be refreshed from the remote source.
     *
     * @return A [Result] containing the timestamp as a [Long] if successful, or an [Error] if unable
     *         to retrieve the timestamp.
     */
    suspend fun getLastFetchedTimestamp(): Result<Long?, Error>

    /**
     * Sets the timestamp for the last successful data fetch operation.
     *
     * This method stores the provided [timestamp], which allows the application to track the timing
     * of data refreshes and manage cache expiration.
     *
     * @param timestamp The timestamp in milliseconds since the epoch to store.
     * @return A [Result] indicating success with [Unit], or an [Error] if storing the timestamp fails.
     */
    suspend fun setLastFetchedTimestamp(timestamp: Long): Result<Unit, Error>

}