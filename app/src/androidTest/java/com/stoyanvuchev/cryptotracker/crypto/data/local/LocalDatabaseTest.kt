package com.stoyanvuchev.cryptotracker.crypto.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNullOrEmpty
import com.stoyanvuchev.cryptotracker.crypto.data.local.entity.CoinEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDatabaseTest {

    private lateinit var localDatabase: LocalDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        localDatabase = LocalDatabaseFactory.createInMemoryInstance(context)
    }

    @After
    fun tearDown() {
        localDatabase.close()
    }

    @Test
    fun insertAndGetAllCoinsList() = runTest {

        val expected = givenCoins
        localDatabase.dao.insertCoins(expected)

        val actual = localDatabase.dao.getPaginatedCoins()
        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun insertAndGetCoinBySymbol() = runTest {

        val given = givenCoins
        localDatabase.dao.insertCoins(given)

        val actual = localDatabase.dao.getCoinBySymbol(given.first().symbol)
        assertThat(actual).isEqualTo(given.first())

    }

    @Test
    fun insertDeleteAndGetAllCoinsList() = runTest {

        localDatabase.dao.insertCoins(givenCoins)
        localDatabase.dao.deleteAllCoins()

        val actual = localDatabase.dao.getPaginatedCoins()
        assertThat(actual).isNullOrEmpty()

    }

    companion object {

        private val givenCoins: List<CoinEntity>
            get() = listOf(
                CoinEntity(
                    askPrice = "0.13375000",
                    askQty = "2997.00000000",
                    bidPrice = "0.13372000",
                    bidQty = "5625.00000000",
                    closeTime = 1729952904130,
                    count = 229601,
                    firstId = 109890809,
                    highPrice = "0.13982000",
                    lastId = 110120409,
                    lastPrice = "0.13371000",
                    lastQty = "2584.00000000",
                    lowPrice = "0.12788000",
                    openPrice = "0.13947000",
                    openTime = 1729866504130,
                    prevClosePrice = "0.13946000",
                    priceChange = "-0.00576000",
                    priceChangePercent = "-4.130",
                    quoteVolume = "51002434.61911000",
                    symbol = "DOGEFDUSD",
                    volume = "380818075.00000000",
                    weightedAvgPrice = "0.13392861"
                ),
                CoinEntity(
                    askPrice = "0.00109200",
                    askQty = "353.50000000",
                    bidPrice = "0.00109100",
                    bidQty = "159.64000000",
                    closeTime = 1729952904434,
                    count = 392,
                    firstId = 168965,
                    highPrice = "0.00118300",
                    lastId = 169356,
                    lastPrice = "0.00109200",
                    lastQty = "18.62000000",
                    lowPrice = "0.00108700",
                    openPrice = "0.00118200",
                    openTime = 1729866504434,
                    prevClosePrice = "0.00118500",
                    priceChange = "-0.00009000",
                    priceChangePercent = "-7.614",
                    quoteVolume = "13.40092709",
                    symbol = "CYBERETH",
                    volume = "11954.61000000",
                    weightedAvgPrice = "0.00112098"
                )
            )

    }

}