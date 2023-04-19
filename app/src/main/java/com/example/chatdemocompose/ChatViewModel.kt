package com.example.chatdemocompose

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatdemocompose.domain.Message
import com.example.chatdemocompose.domain.Message.Companion.SENDER_ME
import com.example.chatdemocompose.usecases.GetLocalMessagesUseCase
import com.example.chatdemocompose.usecases.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class ChatScreenUiState(
    messages: List<Message>
) {

    val currentChannel
        get() = messages.firstOrNull()?.channel

    companion object {
        val EMPTY = ChatScreenUiState(emptyList())
    }

    private val _messages: MutableList<Message> = messages.toMutableStateList()
    val messages: List<Message> = _messages

    fun addMessage(content: Message) {
        _messages.add(0, content)
    }
}

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getLocalMessagesUseCase: GetLocalMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatScreenUiState.EMPTY)
    val uiState = _uiState.asStateFlow()

    private var loadDataJob: Job? = null
    private var sendDataJob: Job? = null

    fun loadLocalData() {
        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch(Dispatchers.IO) {
            val contentData = getLocalMessagesUseCase.invoke()
            withContext(Dispatchers.Main) {
                _uiState.emit(ChatScreenUiState(contentData))
            }
        }
    }

    fun sendMessage(messageText: String) {
        sendDataJob?.cancel()
        sendDataJob = viewModelScope.launch(Dispatchers.IO) {
            val newMessage = Message(
                id = UUID.randomUUID().toString(),
                text = messageText,
                date = System.currentTimeMillis(),
                sender = SENDER_ME,
                channel = uiState.value.currentChannel ?: ""
            )
            sendMessageUseCase.invoke(newMessage)
            // update ui state immediately with new data
            withContext(Dispatchers.Main) {
                _uiState.emit(
                    uiState.value.apply {
                        addMessage(newMessage)
                    }
                )
            }
        }
    }

}