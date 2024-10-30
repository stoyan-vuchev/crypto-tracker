package com.stoyanvuchev.cryptotracker.core.presentation.util

import com.stoyanvuchev.cryptotracker.core.presentation.util.CharUtils.upperFirstAndSecondWordFirstLetter
import com.stoyanvuchev.cryptotracker.core.presentation.util.CharUtils.upperFirstLetter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.TimeZone

/**
 * Extension function for formatting a [Long] timestamp into a readable date and time string.
 *
 * Converts a Unix epoch timestamp (in seconds) into a formatted string that includes the day, date,
 * and time based on the specified timezone.
 *
 * The format produced is: `Day, Date, Year - Time`.
 *
 * Any exception encountered will return a localized error message or "Error" as a fallback.
 *
 * @param timezone The time zone ID used for formatting. Defaults to the system's default time zone.
 * @receiver Long The epoch timestamp in seconds.
 * @return A formatted string with the day, date, and time, or an error message if an exception occurs.
 */
fun Long.dateAndTimeStamp(
    timezone: String = TimeZone.getDefault().id
): String {
    return try {
        val timestampInstant = Instant.ofEpochSecond(this)
        val zonedDateTime = ZonedDateTime.ofInstant(timestampInstant, ZoneId.of(timezone))
        val dayFormatter = DateTimeFormatter.ofPattern("EEE")
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        val day = formatDay(zonedDateTime.format(dayFormatter))
        val date = formatDate(zonedDateTime.year, zonedDateTime.format(dateFormatter))
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val time = zonedDateTime.format(timeFormatter)
        "$day, $date, $year - $time"
    } catch (e: Exception) {
        e.localizedMessage ?: "Error"
    }
}

/**
 * Formats the day string with the first letter capitalized.
 *
 * @param formattedDay The day in a short format (e.g., "Mon").
 * @return The day with the first letter capitalized.
 */
private fun formatDay(formattedDay: String): String {
    return formattedDay.upperFirstLetter()
}

/**
 * Formats the date string by removing the year and excess punctuation, then capitalizing
 * the first letters of the first two words.
 *
 * @param year The year to remove from the formatted date string.
 * @param formattedDate The full date in a medium format (e.g., "Jan 1, 2024").
 * @return The formatted date with only the month and day, formatted appropriately.
 */
private fun formatDate(
    year: Int,
    formattedDate: String
): String {
    return formattedDate.dropLastWhile { !it.isDigit() }
        .replace("$year", "")
        .replace(",", "")
        .trim()
        .upperFirstAndSecondWordFirstLetter()
}