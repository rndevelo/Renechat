package com.rndeveloper.renechat

import com.rndeveloper.renechat.exceptions.CustomException

open class BaseUiState(
    open val isLoading: Boolean,
    open val errorMessage: CustomException?,
)
