package com.rndeveloper.renechat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rndeveloper.renechat.ChatUiState
import com.rndeveloper.renechat.ChatUseCase
import com.rndeveloper.renechat.model.Message
import com.rndeveloper.renechat.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ChatUiState()
    )

    fun getMessages(myUid: String, otherUid: String){
        viewModelScope.launch {
            chatUseCase(myUid to otherUid).collectLatest { result ->
                _state.update { chatState ->
                    chatState.copy(messages = result.messages)
                }
            }
        }
    }

    fun setMessage(myUid: String, otherUid: String, message: Message) {
        viewModelScope.launch {
            chatRepository.setMessage(myUid, otherUid, message).collectLatest {
                _state.update {
//                    Log.d("MESSAGE", "updateViewModel: ${it.text}")
                    it
                }
            }
        }
    }
}