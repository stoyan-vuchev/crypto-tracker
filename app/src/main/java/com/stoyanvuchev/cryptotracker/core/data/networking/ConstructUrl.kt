package com.stoyanvuchev.cryptotracker.core.data.networking

import com.stoyanvuchev.cryptotracker.BuildConfig

/**
 * Constructs a full URL by appending the provided [url] to the base URL if necessary.
 *
 * @param url The partial or full URL string to construct.
 * @return A complete URL as a [String].
 *
 * - If the [url] already contains the base URL defined in [BuildConfig.BASE_URL], it is returned as-is.
 * - If the [url] starts with a forward slash (`/`), the base URL is appended without duplicating the slash.
 * - If the [url] is a relative path, the base URL is prepended.
 */
fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}
