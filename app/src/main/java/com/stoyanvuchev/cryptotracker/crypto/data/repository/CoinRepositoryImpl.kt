package com.stoyanvuchev.cryptotracker.crypto.data.repository

import com.stoyanvuchev.cryptotracker.core.data.repository.CoinRepositoryError
import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.crypto.data.local.LocalDatabaseDao
import com.stoyanvuchev.cryptotracker.crypto.data.mappers.toCoin
import com.stoyanvuchev.cryptotracker.crypto.domain.model.Coin
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.RemoteDataSource
import com.stoyanvuchev.cryptotracker.crypto.domain.repository.CoinRepository

/**
 * Implementation of [CoinRepository] that manages data retrieval and storage for coins.
 *
 * This class fetches data from a [RemoteDataSource] and caches it in a local database using
 * [LocalDatabaseDao]. It first attempts to fetch data from the local database and, if unavailable,
 * retrieves data from the remote source and stores it locally.
 *
 * @property remoteDataSource The data source for remote API interactions.
 * @property localDatabaseDao The data access object for local database CRUD operations.
 */
class CoinRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDatabaseDao: LocalDatabaseDao
) : CoinRepository {

    /**
     * Retrieves a list of all coins, first checking the local database, then fetching from remote if necessary.
     *
     * If the coins are available in the local database, they are returned. Otherwise, the method fetches the
     * data from the [remoteDataSource], inserts it into the local database, and then retrieves and returns it.
     * Returns an error result if the data cannot be retrieved from either source.
     *
     * @return A [Result] containing a list of [Coin] if successful, or a [CoinRepositoryError] if an error occurs.
     */
    override suspend fun getAllCoins(): Result<List<Coin>, Error> {
        return try {

            val coinsList = localDatabaseDao.getPaginatedCoins()
            if (!coinsList.isNullOrEmpty()) {

                Result.Success(coinsList.map { it.toCoin() })

            } else {

                when (val result = remoteDataSource.getCoins()) {

                    is Result.Success -> {

                        localDatabaseDao.insertCoins(result.data)

                        val newCoins = localDatabaseDao.getPaginatedCoins()
                        if (!newCoins.isNullOrEmpty()) {
                            Result.Success(newCoins.map { it.toCoin() })
                        } else {
                            Result.Error(CoinRepositoryError.Unknown)
                        }

                    }

                    is Result.Error -> result

                }

            }

        } catch (e: Exception) {
            Result.Error(CoinRepositoryError.Unknown)
        }
    }

    /**
     * Retrieves a specific coin by its symbol, checking the local database first.
     *
     * If the requested coin is not found in the local database, returns an error. This method also
     * returns an error result if an unexpected issue occurs.
     *
     * @param symbol The symbol of the coin to retrieve.
     * @return A [Result] containing the requested [Coin] if successful, or a [CoinRepositoryError] if an error occurs.
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

}