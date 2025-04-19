package com.techgeni.walletpage.presentation.utils.navgraph

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techgeni.walletpage.presentation.screens.addCard.AddCardScreen
import com.techgeni.walletpage.presentation.screens.wallet.WalletScreen

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "wallet",
    ) {
        composable(
            route = "wallet",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it },animationSpec = tween(1000)) + fadeIn(animationSpec = tween(1000))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it },animationSpec = tween(1000)) + fadeOut(animationSpec = tween(1000))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it },animationSpec = tween(1000)) + fadeIn(animationSpec = tween(1000))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it },animationSpec = tween(1000)) + fadeOut(animationSpec = tween(1000))
            }
        ) {
            WalletScreen(navController = navController)
        }

        composable(
            route = "addCard",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it },animationSpec = tween(1000)) + fadeIn(animationSpec = tween(1000))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it },animationSpec = tween(1000)) + fadeOut(animationSpec = tween(1000))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it },animationSpec = tween(1000)) + fadeIn(animationSpec = tween(1000))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it },animationSpec = tween(1000)) + fadeOut(animationSpec = tween(1000))
            }
        ) {
            AddCardScreen(navController = navController)
        }
    }
}