package com.stoyanvuchev.cryptotracker.core.domain.util

import com.stoyanvuchev.cryptotracker.core.presentation.util.UIText

/**
 * A marker interface used for typealias indicating an error.
 * ```
 * typealias ResultError = Error
 * ```
 */
open class Error(open val uiText: UIText)