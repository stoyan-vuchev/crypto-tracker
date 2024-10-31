package com.stoyanvuchev.cryptotracker.crypto.domain.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

/**
 * Provides a DataStore instance named `crypto_preferences` within the given [Context].
 */
val Context.cryptoPreferences by preferencesDataStore("crypto_preferences")