package com.stoyanvuchev.cryptotracker.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a flow of events as lifecycle-aware updates within a Composable.
 *
 * This function listens to the provided [events] flow and invokes the [onEvent] callback whenever
 * a new event is emitted. The observation starts and stops based on the current [Lifecycle] of the
 * [LocalLifecycleOwner]. It starts collecting events when the lifecycle reaches the [Lifecycle.State.STARTED]
 * state and stops when it moves out of that state.
 *
 * @param T The type of event data in the flow.
 * @param events The flow of events to observe and handle.
 * @param key1 An optional key to control recomposition.
 * @param key2 Another optional key to control recomposition.
 * @param onEvent A callback to handle each event emitted by the flow.
 *
 * Example:
 * ```
 * ObserveAsEvents(events = eventFlow) { event ->
 *     // Handle the event here, such as showing a toast or updating UI state
 * }
 * ```
 *
 * This composable will only collect events while the lifecycle owner is in the STARTED state,
 * ensuring UI updates are in sync with the visible lifecycle of the Composable.
 */
@Composable
fun <T> ObserveAsEvents(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                events.collect(onEvent)
            }
        }
    }
}