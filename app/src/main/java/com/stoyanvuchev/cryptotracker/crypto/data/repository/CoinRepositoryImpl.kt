package com.stoyanvuchev.cryptotracker.crypto.data.repository

import com.stoyanvuchev.cryptotracker.core.data.repository.CoinRepositoryError
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError
import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.core.domain.util.onError
import com.stoyanvuchev.cryptotracker.core.domain.util.onSuccess
import com.stoyanvuchev.cryptotracker.crypto.data.local.LocalDatabaseDao
import com.stoyanvuchev.cryptotracker.crypto.data.mappers.toCoin
import com.stoyanvuchev.cryptotracker.crypto.domain.model.Coin
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.RemoteDataSource
import com.stoyanvuchev.cryptotracker.crypto.domain.preferences.CryptoPreferences
import com.stoyanvuchev.cryptotracker.crypto.domain.repository.CoinRepository
import kotlin.time.Duration.Companion.hours

/**
 * Implementation of [CoinRepository] responsible for managing data retrieval and storage for cryptocurrency coins.
 *
 * This repository prioritizes retrieving data from a local cache ([LocalDatabaseDao]) for efficiency and only fetches
 * from the remote source ([RemoteDataSource]) if the local data is outdated or unavailable. Cached data is updated
 * periodically to keep it in sync with remote data, and preferences ([CryptoPreferences]) are used to track the last
 * successful remote data fetch.
 *
 * @property remoteDataSource The source for remote API interactions related to cryptocurrency data.
 * @property localDatabaseDao Data access object for performing local database operations.
 * @property cryptoPreferences Data source for storing and retrieving timestamp of the last successful remote fetch.
 */
class CoinRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDatabaseDao: LocalDatabaseDao,
    private val cryptoPreferences: CryptoPreferences
) : CoinRepository {

    /**
     * Retrieves a list of all cryptocurrency coins. This method checks the local database for cached data first,
     * only fetching from the remote source if data is missing or outdated (older than one hour).
     *
     * Data retrieved from the remote source is cached locally for future calls. If neither local nor remote data
     * is available, an error is returned.
     *
     * @return A [Result] containing a list of [Coin] objects if successful, or a [CoinRepositoryError] if an error occurs.
     */
    override suspend fun getAllCoins(): Result<List<Coin>, Error> {
        return try {

            val oneHourAgo = System.currentTimeMillis().hours.minus(1.hours).inWholeMilliseconds
            when (val timestampResult = getLastFetchedTimestamp()) {

                is Result.Success -> {

                    if (timestampResult.data == null || oneHourAgo <= timestampResult.data) {
                        fetchCoinsFromRemoteDataSource()
                    } else {

                        val coinsList = localDatabaseDao.getPaginatedCoins()
                        if (!coinsList.isNullOrEmpty()) {
                            Result.Success(coinsList.map { it.toCoin() })
                        } else {
                            fetchCoinsFromRemoteDataSource()
                        }

                    }

                }

                is Result.Error -> Result.Error(timestampResult.error)

            }
        } catch (e: Exception) {
            Result.Error(CoinRepositoryError.Unknown)
        }
    }

    /**
     * Retrieves a specific cryptocurrency coin by its symbol from the local database.
     *
     * If the coin is not found locally, an error is returned. This method only checks the local cache and does
     * not attempt to fetch data from the remote source.
     *
     * @param symbol The unique symbol of the coin to retrieve.
     * @return A [Result] containing the requested [Coin] object if found, or a [CoinRepositoryError] if not found or an error occurs.
     */
    override suspend fun getCoinBySymbol(symbol: String): Result<Coin, Error> {
        return try {
            val coinEntity = localDatabaseDao.getCoinBySymbol(symbol)
            if (coinEntity != null) {
                Result.Success(coinEntity.toCoin())
            } else {
                Result.Error(CoinRepositoryError.Unknown)
            }
        } catch (e: Exception) {
            Result.Error(CoinRepositoryError.Unknown)
        }
    }

    /**
     * Retrieves the timestamp of the last successful data fetch from the remote source.
     *
     * @return A [Result] containing the timestamp as a [Long] if successful, or an [Error] if unable to retrieve the data.
     */
    private suspend fun getLastFetchedTimestamp(): Result<Long?, Error> {
        return try {
            cryptoPreferences
                .getLastFetchedTimestamp()
                .onSuccess { timestamp -> Result.Success(timestamp) }
                .onError { error -> Result.Error(error) }
        } catch (e: Exception) {
            Result.Error(CoinRepositoryError.Unknown)
        }
    }

    /**
     * Fetches a list of coins from the remote source and updates the local cache if the fetch is successful.
     *
     * After a successful fetch, the current timestamp is recorded, and the new data is stored in the local database.
     * If an error occurs during remote fetch or data insertion, an error result is returned.
     *
     * @return A [Result] containing a list of [Coin] objects if successful, or a [CoinRepositoryError] if an error occurs.
     */
    private suspend fun fetchCoinsFromRemoteDataSource(): Result<List<Coin>, Error> {
        return try {
            when (val result = remoteDataSource.getCoins()) {
                is Result.Success -> {

                    val now = System.currentTimeMillis()
                    when (val timestampResult = cryptoPreferences.setLastFetchedTimestamp(now)) {

                        is Result.Success -> {

                            localDatabaseDao.insertCoins(result.data)

                            val newCoins = localDatabaseDao.getPaginatedCoins()
                            if (!newCoins.isNullOrEmpty()) {
                                Result.Success(newCoins.map { it.toCoin() })
                            } else {
                                Result.Error(CoinRepositoryError.Unknown)
                            }

                        }

                        is Result.Error -> Result.Error(timestampResult.error)

                    }
                }

                is Result.Error -> result
            }
        } catch (e: Exception) {
            Result.Error(NetworkError.Unknown)
        }
    }
}