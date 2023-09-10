package com.rndeveloper.renechat.ui.userslist

import com.rndeveloper.renechat.BaseUiState
import com.rndeveloper.renechat.exceptions.CustomException
import com.rndeveloper.renechat.model.UserData

data class UsersListUiState(
    val users: List<UserData>?,
    override val isLoading: Boolean,
    override val errorMessage: CustomException?,
) : BaseUiState(isLoading, errorMessage) {

    constructor() : this(
        users = emptyList(),
        isLoading = false,
        errorMessage = null,
    )
}
