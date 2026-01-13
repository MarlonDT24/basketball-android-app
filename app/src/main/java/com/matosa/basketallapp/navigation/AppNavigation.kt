package com.matosa.basketallapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.matosa.basketallapp.screens.SplashScreen
import com.matosa.basketallapp.screens.AuthScreen
import com.matosa.basketallapp.screens.MainScreen
import com.matosa.basketallapp.screens.ClubsScreen
import com.matosa.basketallapp.screens.CompetitionsScreen
import com.matosa.basketallapp.screens.LiveMatchesScreen
import com.matosa.basketallapp.screens.PlayerProfileScreen



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
 
        composable("auth") {
            AuthScreen(navController)
        }

        composable("main") {
            MainScreen(navController)
        }
        composable("clubs") {
            ClubsScreen(navController)
        }

        composable("competitions") {
            CompetitionsScreen(navController)
        }

        composable("live_matches") {
            LiveMatchesScreen(navController)
        }

        composable("player_profile") {
            PlayerProfileScreen(navController)
        }
    }
}