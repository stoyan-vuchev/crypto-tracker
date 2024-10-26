package com.stoyanvuchev.cryptotracker.crypto.data.networking

import com.stoyanvuchev.cryptotracker.core.data.networking.constructUrl
import com.stoyanvuchev.cryptotracker.core.data.networking.safeCall
import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.core.domain.util.map
import com.stoyanvuchev.cryptotracker.crypto.data.mappers.toCoin
import com.stoyanvuchev.cryptotracker.crypto.data.networking.dto.CoinDto
import com.stoyanvuchev.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.Coin
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.RemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

/**
 * An implementation of [RemoteDataSource] that retrieves coin data from a network source.
 *
 * @property httpClient The [HttpClient] used to make network requests.
 */
class RemoteDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteDataSource {

    /**
     * Fetches a list of coins by making an HTTP GET request to the `/ticker/24hr` endpoint.
     *
     * This method uses [safeCall] to safely handle network and deserialization errors and maps
     * the resulting data transfer objects ([CoinsResponseDto]) to a list of [Coin] objects.
     *
     * @return A [Result] containing either a list of [Coin] objects on success, or a [NetworkError] on failure.
     */
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(urlString = constructUrl("/ticker/24hr"))
        }.map { response ->
            response.map { it.toCoin() }
        }
    }

}