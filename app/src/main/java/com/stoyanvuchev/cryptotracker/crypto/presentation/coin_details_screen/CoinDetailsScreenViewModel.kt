package com.stoyanvuchev.cryptotracker.crypto.presentation.coin_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoyanvuchev.cryptotracker.core.domain.util.map
import com.stoyanvuchev.cryptotracker.core.domain.util.onError
import com.stoyanvuchev.cryptotracker.core.domain.util.onSuccess
import com.stoyanvuchev.cryptotracker.crypto.domain.repository.CoinRepository
import com.stoyanvuchev.cryptotracker.crypto.presentation.mappers.toCoinUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoinDetailsScreenViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(CoinDetailsScreenState())
    val screenState = _screenState.asStateFlow()

    private val _uiActionChannel = Channel<CoinDetailsScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: CoinDetailsScreenUIAction) {
        when (uiAction) {
            is CoinDetailsScreenUIAction.NavigateUp -> sendUIAction(uiAction)
            is CoinDetailsScreenUIAction.LoadCoinDetails -> loadCoinDetails(uiAction.coinSymbol)
            else -> Unit
        }
    }

    private fun sendUIAction(uiAction: CoinDetailsScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

    private fun loadCoinDetails(coinSymbol: String) {
        viewModelScope.launch {

            val coin = withContext(Dispatchers.IO) {
                repository.getCoinBySymbol(coinSymbol)
                    .map { it.toCoinUIModel() }
            }

            coin
                .onSuccess { coinUIModel ->

                    _screenState.update { currentState ->
                        currentState.copy(
                            coin = coinUIModel,
                            isLoading = false
                        )
                    }

                }
                .onError { error ->

                    _screenState.update { currentState ->
                        currentState.copy(isLoading = false)
                    }

                    sendUIAction(CoinDetailsScreenUIAction.Error(error.uiText))

                }

        }
    }

}