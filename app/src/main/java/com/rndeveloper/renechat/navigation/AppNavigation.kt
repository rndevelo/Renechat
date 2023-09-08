package com.rndeveloper.renechat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rndeveloper.renechat.ui.chat.screen.ChatScreen
import com.rndeveloper.renechat.ui.login.screen.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.routes
    ) {
        composable(route = Routes.Login.routes) {
            LoginScreen(navController = navController)
        }
        composable(route = Routes.Chat.routes) {
            ChatScreen(navController = navController)
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    AppNavigation()
}