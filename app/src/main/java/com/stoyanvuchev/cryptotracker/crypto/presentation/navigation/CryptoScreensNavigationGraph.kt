package com.stoyanvuchev.cryptotracker.crypto.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.stoyanvuchev.cryptotracker.core.presentation.util.ObserveAsEvents
import com.stoyanvuchev.cryptotracker.core.presentation.util.asString
import com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen.CoinDetailsScreen
import com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen.CoinDetailsScreenUIAction
import com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen.CoinDetailsScreenViewModel
import com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen.CoinsListScreen
import com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen.CoinsListScreenUIAction
import com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen.CoinsListScreenViewModel

fun NavGraphBuilder.cryptoScreensNavigationGraph(
    navHostController: NavHostController
) = navigation<CryptoScreens.CryptoNavigationGraph>(
    startDestination = CryptoScreens.CoinsListScreen
) {

    composable<CryptoScreens.CoinsListScreen> {

        val viewModel = hiltViewModel<CoinsListScreenViewModel>()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val context = LocalContext.current

        ObserveAsEvents(viewModel.uiActionFlow) { uiAction ->
            when (uiAction) {

                is CoinsListScreenUIAction.ShowCoinDetails -> {
                    navHostController.navigate(
                        CryptoScreens.CoinDetailsScreen(symbol = uiAction.coinSymbol)
                    ) {
                        launchSingleTop = true
                        popUpTo(CryptoScreens.CoinsListScreen) { inclusive = false }
                    }
                }

                is CoinsListScreenUIAction.Error -> {
                    Toast.makeText(
                        context,
                        uiAction.error.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Unit

            }
        }

        CoinsListScreen(
            screenState = screenState,
            onUIAction = viewModel::onUIAction
        )

    }

    composable<CryptoScreens.CoinDetailsScreen> {

        val args = it.toRoute<CryptoScreens.CoinDetailsScreen>()
        val viewModel = hiltViewModel<CoinDetailsScreenViewModel>()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.onUIAction(
                CoinDetailsScreenUIAction.LoadCoinDetails(
                    coinSymbol = args.symbol
                )
            )
        }

        ObserveAsEvents(viewModel.uiActionFlow) { uiAction ->
            when (uiAction) {

                is CoinDetailsScreenUIAction.NavigateUp -> {
                    if (navHostController.previousBackStackEntry != null) {
                        navHostController.navigateUp()
                    }
                }

                is CoinDetailsScreenUIAction.Error -> {
                    Toast.makeText(
                        context,
                        uiAction.error.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Unit

            }
        }

        CoinDetailsScreen(
            screenState = screenState,
            onUIAction = viewModel::onUIAction
        )

    }

}