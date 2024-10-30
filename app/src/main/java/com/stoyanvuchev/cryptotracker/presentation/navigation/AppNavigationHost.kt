package com.stoyanvuchev.cryptotracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.stoyanvuchev.cryptotracker.crypto.presentation.navigation.CryptoScreens
import com.stoyanvuchev.cryptotracker.crypto.presentation.navigation.cryptoScreensNavigationGraph

@Composable
fun AppNavigationHost(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = CryptoScreens.CryptoNavigationGraph
    ) {

        cryptoScreensNavigationGraph(
            navHostController = navHostController
        )

    }

}