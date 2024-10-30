package com.stoyanvuchev.cryptotracker.presentation.navigation

import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        startDestination = CryptoScreens.CryptoNavigationGraph,
        enterTransition = { fadeIn(animationSpec = spring()) },
        exitTransition = { fadeOut(animationSpec = spring()) }
    ) {

        cryptoScreensNavigationGraph(
            navHostController = navHostController
        )

    }

}