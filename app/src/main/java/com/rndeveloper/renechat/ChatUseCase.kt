package com.rndeveloper.renechat

import com.rndeveloper.renechat.model.Message
import com.rndeveloper.renechat.repositories.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val repository: ChatRepository,
) : BaseUseCase<Pair<String, String>, Flow<List<Message>>>() {

    override suspend fun execute(parameters: Pair<String, String>): Flow<List<Message>> = channelFlow {

        // TODO: Validate fields: email restriction and empty fields validations

        // Loading
//        send(LoginUiState().copy(isLoading = true))

        // TODO: Fields validations

        // Do login if fields are valid
        repository.getMessages()
//            .catch { exception ->
//                send(
//                    Message()
//                    LoginUiState().copy(hasError = true, errorMessage = exception.message ?: "Login Error")
//                )
//            }
            .collectLatest { result ->
                result.fold(
                    onSuccess = { sms ->
                        trySend(sms.sortedBy { it.time }.reversed())
                    },
                    onFailure = {
                    }
                )
            }
    }
}