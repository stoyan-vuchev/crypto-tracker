package com.stoyanvuchev.cryptotracker.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Factory object for creating a configured [HttpClient] instance with a specified [HttpClientEngine].
 *
 * The created [HttpClient] includes the following configurations:
 * - **Logging**: Logs all HTTP requests and responses with [LogLevel.ALL], using [Logger.ANDROID].
 * - **Content Negotiation**: Installs JSON serialization/deserialization support, configured to ignore unknown keys.
 * - **Default Request**: Sets the default content type to `application/json`.
 */
object HttpClientFactory {

    /**
     * Creates a new instance of [HttpClient] using the provided [engine] with predefined plugins and settings.
     *
     * @param engine The [HttpClientEngine] to be used for executing requests (e.g., CIO, OkHttp).
     * @return A configured [HttpClient] instance.
     */
    fun createInstance(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {

            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                contentType(
                    ContentType.Application.Json
                )
            }

        }
    }

}