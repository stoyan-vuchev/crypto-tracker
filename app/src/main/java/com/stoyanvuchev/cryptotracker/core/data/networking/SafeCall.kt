package com.stoyanvuchev.cryptotracker.core.data.networking

import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

/**
 * Executes an HTTP call safely, handling network and serialization errors, and returning a [Result].
 *
 * @param T The expected type of the response body.
 * @param execute A function that performs the HTTP request and returns an [HttpResponse].
 * @return A [Result] containing either the parsed body of type [T] on success, or a [NetworkError] on failure.
 *
 * This function handles the following exceptions:
 * - **UnresolvedAddressException**: Returns a [NetworkError.NoInternet] error, indicating no network connection.
 * - **SerializationException**: Returns a [NetworkError.Serialization] error, indicating a failure in data serialization.
 * - **Other Exceptions**: Returns a [NetworkError.Unknown] error if an unexpected exception occurs.
 *
 * If the HTTP call succeeds, the response is passed to [responseToResult] for status-based handling.
 * The function also checks the coroutine's active state to avoid continuing if the coroutine is canceled.
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {

    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NoInternet)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.Serialization)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.Unknown)
    }

    return responseToResult(response)

}