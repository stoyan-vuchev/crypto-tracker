package com.stoyanvuchev.cryptotracker.crypto.domain.networking

import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError
import com.stoyanvuchev.cryptotracker.core.domain.util.Result

/**
 * A data source interface for retrieving cryptocurrency data.
 */
interface RemoteDataSource {

    /**
     * Fetches a list of coins with their current data.
     *
     * @return A [Result] containing either a list of [Coin] objects on success, or a [NetworkError] on failure.
     */
    suspend fun getCoins(): Result<List<Coin>, NetworkError>

}