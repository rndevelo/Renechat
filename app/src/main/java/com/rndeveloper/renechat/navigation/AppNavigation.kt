package com.rndeveloper.renechat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rndeveloper.renechat.ui.chatscreen.ChatScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Chat.routes
    ) {
        composable(route = Routes.Chat.routes) {
            ChatScreen()
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    AppNavigation()
}