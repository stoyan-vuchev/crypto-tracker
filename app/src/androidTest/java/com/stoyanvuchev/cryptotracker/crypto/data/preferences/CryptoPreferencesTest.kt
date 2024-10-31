package com.stoyanvuchev.cryptotracker.crypto.data.preferences

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.stoyanvuchev.cryptotracker.core.domain.util.onError
import com.stoyanvuchev.cryptotracker.core.domain.util.onSuccess
import com.stoyanvuchev.cryptotracker.core.presentation.util.asString
import com.stoyanvuchev.cryptotracker.crypto.domain.preferences.CryptoPreferences
import com.stoyanvuchev.cryptotracker.crypto.domain.preferences.cryptoPreferences
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CryptoPreferencesTest {

    private lateinit var preferences: CryptoPreferences

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>().applicationContext
        preferences = CryptoPreferencesImpl(preferences = context.cryptoPreferences)
    }

    @Test
    fun setAndGetLastFetchedTimestamp() = runTest {

        val expected = 10_000L

        preferences
            .setLastFetchedTimestamp(expected)
            .onSuccess {

                preferences
                    .getLastFetchedTimestamp()
                    .onSuccess { timestamp ->
                        assertThat(timestamp).isEqualTo(expected)
                    }
                    .onError { error ->
                        val context = ApplicationProvider.getApplicationContext<Context>()
                        throw Exception(error.uiText.asString(context.applicationContext))
                    }

            }
            .onError { error ->
                val context = ApplicationProvider.getApplicationContext<Context>()
                throw Exception(error.uiText.asString(context.applicationContext))
            }

    }

}