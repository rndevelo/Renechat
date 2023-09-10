package com.rndeveloper.renechat.ui.login.usecases

import com.digitalsolution.familyfilmapp.ui.screens.login.usecases.isEmailValid
import com.digitalsolution.familyfilmapp.ui.screens.login.usecases.isPasswordValid
import com.rndeveloper.renechat.BaseUseCase
import com.rndeveloper.renechat.exceptions.CustomException.GenericException
import com.rndeveloper.renechat.exceptions.LoginException.EmailInvalidFormat
import com.rndeveloper.renechat.exceptions.LoginException.PasswordInvalidFormat
import com.rndeveloper.renechat.model.UserData
import com.rndeveloper.renechat.repositories.LoginRepository
import com.rndeveloper.renechat.ui.login.LoginScreenState
import com.rndeveloper.renechat.ui.login.LoginUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class LoginEmailPassUseCase @Inject constructor(
    private val repository: LoginRepository,
) : BaseUseCase<Pair<String, String>, Flow<LoginUiState>>() {

    override suspend fun execute(parameters: Pair<String, String>): Flow<LoginUiState> =
        channelFlow {
            val (email, pass) = parameters

            // Loading
            send(
                LoginUiState().copy(
                    screenState = LoginScreenState.Login(),
                    isLoading = true,
                )
            )

            when {

                !email.isEmailValid() && !pass.isPasswordValid() -> {
                    send(
                        LoginUiState().copy(
                            screenState = LoginScreenState.Login(),
                            emailErrorMessage = EmailInvalidFormat(),
                            passErrorMessage = PasswordInvalidFormat(),
                            isLoading = false
                        )
                    )
                }

                !email.isEmailValid() -> {
                    send(
                        LoginUiState().copy(
                            screenState = LoginScreenState.Login(),
                            emailErrorMessage = EmailInvalidFormat(),
                            isLoading = false
                        )
                    )
                }

                !pass.isPasswordValid() -> {
                    send(
                        LoginUiState().copy(
                            screenState = LoginScreenState.Login(),
                            passErrorMessage = PasswordInvalidFormat(),
                            isLoading = false
                        )
                    )
                }

                else -> {
                    repository.loginEmailPass(email, pass)
                        .catch { exception ->
                            send(
                                LoginUiState().copy(
                                    screenState = LoginScreenState.Login(),
                                    isLoading = false,
                                    errorMessage = GenericException(exception.message ?: "Login Error"),
                                )
                            )
                        }
                        .collectLatest { result ->
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
                                            errorMessage = GenericException(exception.message ?: "Login Failure")
                                        )
                                    )
                                },
                            )
                        }
                }
            }
        }
}
