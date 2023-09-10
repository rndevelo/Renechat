package com.rndeveloper.renechat.navigation

sealed class Routes(val routes: String) {
    object Login : Routes("Login")
    object UsersList : Routes("UsersList")

    object Chat: Routes("Chat/{my_uid}/{other_uid}"){
        fun passId(myUid: String, otherUid: String): String {
            return "Chat/$myUid/$otherUid"
        }
    }
}