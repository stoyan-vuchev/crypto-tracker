package com.stoyanvuchev.cryptotracker.crypto.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isNotNull
import com.stoyanvuchev.cryptotracker.core.data.networking.HttpClientFactory
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import com.stoyanvuchev.cryptotracker.crypto.data.local.LocalDatabase
import com.stoyanvuchev.cryptotracker.crypto.data.local.LocalDatabaseFactory
import com.stoyanvuchev.cryptotracker.crypto.data.networking.RemoteDataSourceImpl
import com.stoyanvuchev.cryptotracker.crypto.data.preferences.CryptoPreferencesImpl
import com.stoyanvuchev.cryptotracker.crypto.domain.model.Coin
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.RemoteDataSource
import com.stoyanvuchev.cryptotracker.crypto.domain.preferences.cryptoPreferences
import com.stoyanvuchev.cryptotracker.crypto.domain.repository.CoinRepository
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoinRepositoryTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDatabase: LocalDatabase
    private lateinit var coinRepository: CoinRepository

    @Before
    fun setUp() {

        val httpClient = HttpClientFactory.createInstance(CIO.create())
        remoteDataSource = RemoteDataSourceImpl(httpClient)

        val context = ApplicationProvider.getApplicationContext<Context>().applicationContext
        localDatabase = LocalDatabaseFactory.createInMemoryInstance(context)

        val cryptoPreferences = CryptoPreferencesImpl(context.cryptoPreferences)

        coinRepository = CoinRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDatabaseDao = localDatabase.dao,
            cryptoPreferences = cryptoPreferences
        )

    }

    @After
    fun tearDown() {
        localDatabase.close()
    }

    @Test
    fun getAllCoinsWithoutException() = runTest {

        val result = coinRepository.getAllCoins()
        var coins: List<Coin>? = null

        when (result) {

            is Result.Success -> {
                coins = result.data
                println("CoinRepositoryTest: Coins: $coins")
            }

            is Result.Error -> println("CoinRepositoryTest: Error: ${result.error}")

        }

        assertThat(coins).isNotNull()

    }

}