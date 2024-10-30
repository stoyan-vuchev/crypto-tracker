package com.stoyanvuchev.cryptotracker.core.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Represents a UI text model, allowing for either a basic string or a string resource
 * with optional formatting arguments. This can be useful for handling strings in a
 * UI-agnostic way, accommodating both literal and localized resource strings.
 */
sealed interface UIText {

    /**
     * Represents a basic, non-resource, non-localized string.
     *
     * @property value The string value to display.
     */
    data class BasicString(
        val value: String
    ) : UIText

    /**
     * Represents a string resource that can be localized.
     *
     * @property resId The resource ID of the string.
     * @property formatArgs Optional formatting arguments for the string resource.
     */
    class StringResource(
        @StringRes val resId: Int,
        vararg val formatArgs: Any
    ) : UIText

}

/**
 * Converts [UIText] to a displayable string within a Composable context.
 *
 * @return The string representation of [UIText], which could be a literal string or
 * a localized string resource with formatted arguments.
 *
 * Example:
 * ```
 * val uiText = UIText.StringResource(R.string.welcome_message, userName)
 * Text(text = uiText.asString())
 * ```
 */
@Composable
fun UIText.asString(): String {
    return when (this) {
        is UIText.BasicString -> this.value
        is UIText.StringResource -> stringResource(resId, formatArgs)
    }
}

/**
 * Converts [UIText] to a displayable string using the provided [Context].
 *
 * @param context The context used to access resources.
 * @return The string representation of [UIText], resolved either as a basic string
 * or a localized string resource with formatted arguments.
 *
 * Example:
 * ```
 * val uiText = UIText.StringResource(R.string.error_message)
 * val context = LocalContext.current
 *
 * ObserveAsEvents(viewModel.uiActionFlow) { uiAction ->
 *     showSnackbar(message = uiText.asString(context)
 * }
 * ```
 */
fun UIText.asString(context: Context): String {
    return when (this) {
        is UIText.BasicString -> this.value
        is UIText.StringResource -> context.resources.getString(resId, formatArgs)
    }
}