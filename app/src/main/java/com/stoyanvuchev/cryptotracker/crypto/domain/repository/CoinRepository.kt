package com.stoyanvuchev.cryptotracker.crypto.domain.repository

import com.stoyanvuchev.cryptotracker.core.domain.util.Error
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.crypto.domain.model.Coin

/**
 * Repository interface for fetching coin data.
 *
 * This interface provides methods for retrieving coin information from a data source.
 */
interface CoinRepository {

    /**
     * Retrieves all coins.
     *
     * This method returns either a successful result containing a list of [Coin] objects or an error
     * if the data cannot be fetched.
     *
     * @return A [Result] containing a list of [Coin] or an [Error].
     */
    suspend fun getAllCoins(): Result<List<Coin>, Error>

    /**
     * Retrieves information for a specific coin by its symbol.
     *
     * This method returns either a successful result containing the requested [Coin] or an error if
     * the coin cannot be found or fetched.
     *
     * @param symbol The symbol of the coin to retrieve.
     * @return A [Result] containing a [Coin] object or an [Error].
     */
    suspend fun getCoinBySymbol(symbol: String): Result<Coin, Error>

}