package com.stoyanvuchev.cryptotracker.crypto.data.networking

import assertk.assertThat
import assertk.assertions.isNotNull
import com.stoyanvuchev.cryptotracker.core.data.networking.HttpClientFactory
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.Coin
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.RemoteDataSource
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        val httpClient = HttpClientFactory.createInstance(CIO.create())
        remoteDataSource = RemoteDataSourceImpl(httpClient)
    }

    @Test
    fun `Fetch all coins with no exception`() = runTest {

        val result = remoteDataSource.getCoins()
        var coins: List<Coin>? = null

        when (result) {

            is Result.Success -> {
                coins = result.data
            }

            is Result.Error -> {
                println("RemoteDataSourceTest: Error: ${result.error}")
            }

        }

        println("RemoteDataSourceTest: Coins: $coins")
        assertThat(coins).isNotNull()

    }

}