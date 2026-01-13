package com.matosa.basketallapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.matosa.basketallapp.navigation.AppNavigation
import com.matosa.basketallapp.ui.theme.BasketallAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            BasketallAppTheme {
                AppNavigation()
            }
        }
    }
}