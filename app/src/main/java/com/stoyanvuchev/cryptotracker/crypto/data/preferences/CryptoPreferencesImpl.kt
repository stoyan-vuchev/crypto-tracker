package com.stoyanvuchev.cryptotracker.crypto.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.crypto.domain.preferences.CryptoPreferences
import com.stoyanvuchev.cryptotracker.crypto.domain.preferences.CryptoPreferencesError
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Implementation of [CryptoPreferences] that uses Jetpack DataStore to store and retrieve
 * cryptocurrency-related preferences, such as the timestamp of the last data fetch operation.
 *
 * This class enables persistent storage of preferences by leveraging the DataStore API,
 * allowing the application to maintain cache timestamps and manage data refresh intervals.
 *
 * @property preferences A [DataStore] instance for accessing preference data.
 */
class CryptoPreferencesImpl @Inject constructor(
    private val preferences: DataStore<Preferences>
) : CryptoPreferences {

    /**
     * Retrieves the timestamp of the last successful data fetch operation from DataStore.
     *
     * If the timestamp exists, it is returned; otherwise, `null` is provided. This timestamp
     * helps to determine if the cached data is up-to-date.
     *
     * @return A [Result] containing the timestamp as a [Long] if successful, or an [Error]
     *         if an error occurs during retrieval.
     */
    override suspend fun getLastFetchedTimestamp(): Result<Long?, Error> {
        return try {
            val timestamp = preferences.data.first()[LAST_FETCHED_TIMESTAMP]
            Result.Success(timestamp)
        } catch (e: Exception) {
            Result.Error(CryptoPreferencesError.LastFetchedTimestampError)
        }
    }

    /**
     * Stores the timestamp of the most recent data fetch operation in DataStore.
     *
     * This allows the application to persist the time of the last data refresh, enabling
     * cache expiration management.
     *
     * @param timestamp The timestamp in milliseconds since the epoch to be stored.
     * @return A [Result] indicating success with [Unit], or an [Error] if storing the timestamp fails.
     */
    override suspend fun setLastFetchedTimestamp(timestamp: Long): Result<Unit, Error> {
        return try {
            preferences.edit { it[LAST_FETCHED_TIMESTAMP] = timestamp }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(CryptoPreferencesError.LastFetchedTimestampError)
        }
    }

    companion object {

        /**
         * Preference key for storing the timestamp of the last data fetch operation.
         */
        private val LAST_FETCHED_TIMESTAMP = longPreferencesKey("last_fetched_timestamp")

    }

}