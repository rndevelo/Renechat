package com.rndeveloper.renechat.model

data class UserData(
    val email: String = "",
    val pass: String = "",
    val username: String = "",
    val uid: String = "",
    val chats: List<String> = emptyList()
)
