package com.stoyanvuchev.cryptotracker.crypto.presentation.navigation

import com.stoyanvuchev.cryptotracker.core.presentation.navigation.Screen
import kotlinx.serialization.Serializable

@Serializable
object CryptoScreens {

    @Serializable
    object CryptoNavigationGraph

    @Serializable
    object CoinsListScreen : Screen()

}