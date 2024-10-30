package com.stoyanvuchev.cryptotracker.core.presentation.util

import java.util.Locale

/**
 * Utility object for character and string manipulation.
 *
 * Provides extension functions to modify string capitalization, including
 * capitalizing the first letter of a string and capitalizing the first letters
 * of the first two words in a string.
 */
object CharUtils {

    /**
     * Capitalizes the first letter of the string.
     *
     * If the first character is lowercase, it is converted to uppercase.
     * If the character is already uppercase or non-alphabetic, the string remains unchanged.
     * Any exception encountered returns a localized error message or "Error" as a fallback.
     *
     * @receiver String The input string.
     * @return The string with the first letter capitalized, or an error message if an exception occurs.
     */
    fun String.upperFirstLetter(): String {
        return try {
            this.replaceFirstChar { char ->
                if (char.isLowerCase()) {
                    char.titlecase(Locale.ROOT)
                } else char.toString()
            }
        } catch (e: Exception) {
            e.localizedMessage ?: "Error"
        }
    }

    /**
     * Capitalizes the first letter of each of the first two words in the string.
     *
     * Splits the input string by spaces and applies [upperFirstLetter] to each word,
     * joining them back into a single string with spaces. If any exception occurs,
     * returns a localized error message or "Error" as a fallback.
     *
     * @receiver String The input string.
     * @return The string with the first letters of the first two words capitalized,
     * or an error message if an exception occurs.
     */
    fun String.upperFirstAndSecondWordFirstLetter(): String {
        return try {
            this.split(" ").joinToString(" ") { it.upperFirstLetter() }
        } catch (e: Exception) {
            e.localizedMessage ?: "Error"
        }
    }

}