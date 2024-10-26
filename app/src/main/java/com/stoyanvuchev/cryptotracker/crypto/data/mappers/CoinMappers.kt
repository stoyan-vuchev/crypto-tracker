package com.stoyanvuchev.cryptotracker.crypto.data.mappers

import com.stoyanvuchev.cryptotracker.crypto.data.networking.dto.CoinDto
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.Coin

/**
 * Converts a [CoinDto] data transfer object to a [Coin] domain model.
 *
 * This extension function maps each property of the [CoinDto] instance to the corresponding
 * property of the [Coin] instance.
 *
 * @return A [Coin] object populated with the values from the [CoinDto].
 */
fun CoinDto.toCoin(): Coin {
    return Coin(
        askPrice = askPrice,
        askQty = askQty,
        bidPrice = bidPrice,
        bidQty = bidQty,
        closeTime = closeTime,
        count = count,
        firstId = firstId,
        highPrice = highPrice,
        lastId = lastId,
        lastPrice = lastPrice,
        lastQty = lastQty,
        lowPrice = lowPrice,
        openPrice = openPrice,
        openTime = openTime,
        prevClosePrice = prevClosePrice,
        priceChange = priceChange,
        priceChangePercent = priceChangePercent,
        quoteVolume = quoteVolume,
        symbol = symbol,
        volume = volume,
        weightedAvgPrice = weightedAvgPrice
    )
}