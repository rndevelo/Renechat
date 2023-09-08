package com.rndeveloper.renechat.navigation

sealed class Routes(val routes: String) {
    object Login : Routes("Login")
    object Chat : Routes("Chat")
}