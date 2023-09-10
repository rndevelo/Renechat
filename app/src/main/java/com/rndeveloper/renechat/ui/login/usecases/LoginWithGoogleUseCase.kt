package com.rndeveloper.renechat.ui.login.usecases

import com.rndeveloper.renechat.BaseUseCase
import com.rndeveloper.renechat.exceptions.CustomException
import com.rndeveloper.renechat.model.UserData
import com.rndeveloper.renechat.repositories.LoginRepository
import com.rndeveloper.renechat.ui.login.LoginScreenState
import com.rndeveloper.renechat.ui.login.LoginUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val repository: LoginRepository,
) : BaseUseCase<String, Flow<LoginUiState>>() {

    override suspend fun execute(parameters: String): Flow<LoginUiState> = channelFlow {
        send(
            LoginUiState().copy(
                screenState = LoginScreenState.Login(),
                isLoading = true
            )
        )

        repository.loginWithGoogle(parameters)
            .catch { exception ->
                send(
                    LoginUiState().copy(
                        screenState = LoginScreenState.Login(),
                        isLoading = false,
                        errorMessage = CustomException.GenericException(exception.message ?: "Google Login Error"),
                    )
                )
            }
            .collect { result ->
                result.fold(
                    onSuccess = { authResult ->
                        send(
                            LoginUiState().copy(
                                screenState = LoginScreenState.Login(),
                                userData = UserData(
                                    email = "",
                                    pass = "",
                                    username = "",
                                ),
                                isLogged = authResult.user != null,
                                isLoading = false
                            )
                        )
                    },
                    onFailure = { exception ->
                        send(
                            LoginUiState().copy(
                                screenState = LoginScreenState.Login(),
                                isLoading = false,
                                errorMessage = CustomException.GenericException(
                                    exception.message ?: "Google Login Failure"
                                )
                            )
                        )
                    }
                )
            }
    }
}
