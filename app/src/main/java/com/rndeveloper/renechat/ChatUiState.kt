package com.rndeveloper.renechat

import com.rndeveloper.renechat.model.Message

data class ChatUiState(
    val screenState: LoginScreenState,
    val messages: List<Message>?,
    val emailErrorMessage: String?,
    val passErrorMessage: String?,
    override val isLoading: Boolean,
    override val errorMessage: String?,
) : BaseUiState(isLoading, errorMessage) {

    constructor() : this(
        screenState = LoginScreenState.Login,
        messages = emptyList(),
        emailErrorMessage = null,
        passErrorMessage = null,
        isLoading = false,
        errorMessage = null,
    )
}

sealed class LoginScreenState {
    object Login : LoginScreenState()
    object Register : LoginScreenState()
}
