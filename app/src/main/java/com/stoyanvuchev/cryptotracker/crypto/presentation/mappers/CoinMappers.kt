package com.stoyanvuchev.cryptotracker.crypto.presentation.mappers

import com.stoyanvuchev.cryptotracker.crypto.domain.model.Coin
import com.stoyanvuchev.cryptotracker.crypto.presentation.model.CoinUIModel

/**
 * Converts a [Coin] entity to a [CoinUIModel] presentation model.
 *
 * This extension function maps each property of the [Coin] instance to the corresponding
 * property of the [CoinUIModel] instance.
 *
 * @return A [CoinUIModel] object populated with the values from the [Coin].
 */
fun Coin.toCoinUIModel(): CoinUIModel {
    return CoinUIModel(
        id = id,
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