package com.stoyanvuchev.cryptotracker.core.data.networking

import com.stoyanvuchev.cryptotracker.core.domain.networking.util.NetworkError
import com.stoyanvuchev.cryptotracker.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/**
 * Converts an [HttpResponse] to a [Result] based on the HTTP status code and response body.
 *
 * @param T The expected type of the response body.
 * @param response The [HttpResponse] to be converted.
 * @return A [Result] containing either the parsed body of type [T] on success, or a [NetworkError] on failure.
 *
 * - **2xx (Success)**: Attempts to parse the response body as type [T]. If deserialization fails,
 *   a [NetworkError.Serialization] error is returned.
 * - **408 (Request Timeout)**: Returns a [NetworkError.RequestTimeout] error.
 * - **429 (Too Many Requests)**: Returns a [NetworkError.TooManyRequests] error.
 * - **5xx (Server Error)**: Returns a [NetworkError.ServerError] error.
 * - **Other Status Codes**: Returns a [NetworkError.Unknown] error.
 *
 * @throws NoTransformationFoundException if the response body cannot be transformed into type [T].
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when (response.status.value) {

        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.Serialization)
            }
        }

        408 -> Result.Error(NetworkError.RequestTimeout)

        429 -> Result.Error(NetworkError.TooManyRequests)

        in 500..599 -> Result.Error(NetworkError.ServerError)

        else -> Result.Error(NetworkError.Unknown)

    }
}