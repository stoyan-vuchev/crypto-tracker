package com.stoyanvuchev.cryptotracker.core.domain.util

/**
 * A sealed interface representing a result that can either be a success with data or an error.
 *
 * @param D The type of the success data.
 * @param E The type of the error, constrained to be a subtype of [Error].
 */
sealed interface Result<out D, out E : Error> {

    /**
     * Represents a successful result with the provided data.
     *
     * @param data The success data.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents an error result with the provided error information.
     *
     * @param error The error instance, constrained to be a subtype of [ResultError].
     */
    data class Error<out E : ResultError>(val error: E) : Result<Nothing, E>

}

/**
 * Maps the data in a [Result.Success] to a new type, while preserving any error.
 *
 * @param map A function to transform the success data.
 * @return A new [Result] containing the mapped success data or the original error.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Converts a [Result] to an [EmptyResult], ignoring any success data and preserving any error.
 *
 * @return A new [EmptyResult] with `Unit` as the success data type or the original error.
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

/**
 * Executes a given action if the result is successful, passing the success data to the action.
 *
 * @param action A lambda to be executed with the success data.
 * @return The original [Result] after executing the action, for chaining calls.
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {

        is Result.Error -> this

        is Result.Success -> {
            action(data)
            this
        }

    }
}

/**
 * Executes a given action if the result is an error, passing the error to the action.
 *
 * @param action A lambda to be executed with the error data.
 * @return The original [Result] after executing the action, for chaining calls.
 */
inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {

        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this

    }
}

/**
 * Alias for the error type, constrained to a subtype of [Error].
 */
typealias ResultError = Error

/**
 * Alias for a [Result] with `Unit` as the success data type, used when the result does not return data.
 */
typealias EmptyResult<E> = Result<Unit, E>