package com.rndeveloper.renechat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rndeveloper.renechat.ui.chat.screen.ChatScreen
import com.rndeveloper.renechat.ui.login.screen.LoginScreen
import com.rndeveloper.renechat.ui.userslist.screen.UsersListScreen

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
        composable(route = Routes.UsersList.routes) {
            UsersListScreen(navController = navController)
        }
        composable(
            route = Routes.Chat.routes,
            arguments = listOf(
                navArgument("my_uid") { type = NavType.StringType },
                navArgument("other_uid") { type = NavType.StringType }
            ),
        ) {
            val myUid = it.arguments?.getString("my_uid")
            val otherUid = it.arguments?.getString("other_uid")
            if (myUid != null && otherUid != null) {
                ChatScreen(navController = navController, myUid = myUid, otherUid = otherUid)
            }
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    AppNavigation()
}