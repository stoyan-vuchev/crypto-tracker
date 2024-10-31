package com.stoyanvuchev.cryptotracker.crypto.domain.networking

import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.crypto.data.local.entity.CoinEntity

/**
 * A data source interface for retrieving cryptocurrency data.
 */
interface RemoteDataSource {

    /**
     * Fetches a list of coins with their current data.
     *
     * @return A [Result] containing either a list of [CoinEntity] objects on success, or a [NetworkError] on failure.
     */
    suspend fun getCoins(): Result<List<CoinEntity>, NetworkError>

}