package com.rndeveloper.renechat.ui.userslist

import com.rndeveloper.renechat.BaseUseCase
import com.rndeveloper.renechat.repositories.UsersListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class UsersListUseCase @Inject constructor(
    private val repository: UsersListRepository,
) : BaseUseCase<Pair<String, String>, Flow<UsersListUiState>>() {

    override suspend fun execute(parameters: Pair<String, String>): Flow<UsersListUiState> =
        channelFlow {

            // TODO: Validate fields: email restriction and empty fields validations

            // Loading
            send(UsersListUiState().copy(isLoading = true))

            // TODO: Fields validations

            // Do login if fields are valid
            repository.getUsers()
//            .catch { exception ->
//                send(
//                    Message()
//                    LoginUiState().copy(hasError = true, errorMessage = exception.message ?: "Login Error")
//                )
//            }
                .collectLatest { result ->
                    result.fold(
                        onSuccess = { users ->
                            val usersListUiState =
                                UsersListUiState().copy(users = users)
                            trySend(usersListUiState)
                        },
                        onFailure = {}
                    )
                }
        }
}