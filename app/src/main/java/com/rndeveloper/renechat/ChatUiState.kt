package com.rndeveloper.renechat

import com.rndeveloper.renechat.exceptions.CustomException
import com.rndeveloper.renechat.model.Message

data class ChatUiState(
    val messages: List<Message>?,
    override val isLoading: Boolean,
    override val errorMessage: CustomException?,
) : BaseUiState(isLoading, errorMessage) {

    constructor() : this(
        messages = emptyList(),
        isLoading = false,
        errorMessage = null,
    )
}

