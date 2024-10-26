package com.stoyanvuchev.cryptotracker.core.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.ScrimStyle
import com.stoyanvuchev.systemuibarstweaker.rememberSystemUIBarsTweaker

@Composable
fun CryptoTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val systemUIBarsTweaker = rememberSystemUIBarsTweaker()

    DisposableEffect(systemUIBarsTweaker, darkTheme) {
        systemUIBarsTweaker.tweakSystemBarsStyle(
            statusBarStyle = systemUIBarsTweaker.statusBarStyle.copy(
                darkIcons = !darkTheme,
                scrimStyle = ScrimStyle.None
            ),
            navigationBarStyle = systemUIBarsTweaker.navigationBarStyle.copy(
                darkIcons = !darkTheme,
                scrimStyle = if (systemUIBarsTweaker.isGestureNavigationEnabled) ScrimStyle.None
                else ScrimStyle.System
            )
        )
        onDispose { }
    }

    ProvideSystemUIBarsTweaker(
        systemUIBarsTweaker = systemUIBarsTweaker,
        content = {

            val colorScheme = when {

                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

                    val context = LocalContext.current

                    if (darkTheme) dynamicDarkColorScheme(context)
                    else dynamicLightColorScheme(context)

                }

                darkTheme -> darkColorScheme()
                else -> lightColorScheme()

            }

            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )

        }
    )

}