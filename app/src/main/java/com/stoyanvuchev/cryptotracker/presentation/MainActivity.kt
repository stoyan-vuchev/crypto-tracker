package com.stoyanvuchev.cryptotracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.stoyanvuchev.cryptotracker.core.presentation.theme.CryptoTrackerTheme
import com.stoyanvuchev.cryptotracker.presentation.navigation.AppNavigationHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable Edge-To-Edge.
        enableEdgeToEdge()

        setContent {
            val navHostController = rememberNavController()
            CryptoTrackerTheme { AppNavigationHost(navHostController) }
        }

    }

}