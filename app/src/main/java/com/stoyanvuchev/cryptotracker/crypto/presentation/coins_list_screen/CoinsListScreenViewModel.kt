package com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoinsListScreenViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(CoinsListScreenState())
    val screenState = _screenState
        .onStart { loadCoins() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CoinsListScreenState()
        )

    private val uiActionChannel = Channel<CoinsListScreenUIAction>()
    val uiActionFlow = uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: CoinsListScreenUIAction) {
        when (uiAction) {
            is CoinsListScreenUIAction.LoadCoins -> loadCoins()
            is CoinsListScreenUIAction.ShowCoinDetails -> sendUIAction(uiAction)
            is CoinsListScreenUIAction.Error -> sendUIAction(uiAction)
        }
    }

    private fun sendUIAction(uiAction: CoinsListScreenUIAction) {
        viewModelScope.launch { uiActionChannel.send(uiAction) }
    }

    private fun loadCoins() {

        _screenState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch {

            delay(256L) // A bit of artificial delay for displaying the loading state.

            val coins = withContext(Dispatchers.IO) {
                repository.getAllCoins().map { list ->
                    list.map { it.toCoinUIModel() }
                }
            }

            coins
                .onSuccess { newCoins ->

                    _screenState.update { currentState ->
                        currentState.copy(
                            coins = newCoins,
                            isLoading = false
                        )
                    }

                }
                .onError { error ->

                    _screenState.update { currentState ->
                        currentState.copy(isLoading = false)
                    }

                    sendUIAction(CoinsListScreenUIAction.Error(error.uiText))

                }

        }
    }


}