package com.rndeveloper.renechat.navigation

sealed class Routes(val routes: String) {
    object Chat : Routes("Chat")
}