package com.example.chatdemocompose

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatdemocompose.domain.Message
import com.example.chatdemocompose.usecases.GetLocalMessagesUseCase
import com.example.chatdemocompose.usecases.RemoveLocalMessagesUseCase
import com.example.chatdemocompose.usecases.SendMessageUseCase
import com.example.shared.DummyFactory
import com.example.shared.domain.Message.Companion.SENDER_ME
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
    private val removeLocalMessagesUseCase: RemoveLocalMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatScreenUiState.EMPTY)
    val uiState = _uiState.asStateFlow()

    private var loadDataJob: Job? = null
    private var sendDataJob: Job? = null
    private var deleteDataJob: Job? = null

    fun deleteMessages(channel: String) {
        deleteDataJob?.cancel()
        deleteDataJob = viewModelScope.launch(Dispatchers.IO) {
            removeLocalMessagesUseCase.invoke(channel)
            withContext(Dispatchers.Main) {
                _uiState.emit(ChatScreenUiState(emptyList()))
            }
        }
    }

    fun generateMessages(channel: String, count: Int) {
        val generatedMessages = DummyFactory.generateReceivedMessages(count)
        sendDataJob?.cancel()
        sendDataJob = viewModelScope.launch(Dispatchers.IO) {
            val newUiState = uiState.value
            generatedMessages.forEach {
                val msg = it.copy(
                    channel = channel,
                    date = System.currentTimeMillis()
                )
                sendMessageUseCase.invoke(Message(msg))
                newUiState.addMessage(Message(msg))
            }
            withContext(Dispatchers.Main) {
                _uiState.emit(newUiState)
            }
        }
    }

    fun loadMessages(channel: String) {
        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch(Dispatchers.IO) {
            val contentData = getLocalMessagesUseCase.invoke(channel)
            withContext(Dispatchers.Main) {
                _uiState.emit(ChatScreenUiState(contentData))
            }
        }
    }

    fun sendMessage(messageText: String, sender: String = SENDER_ME, channel: String) {
        sendDataJob?.cancel()
        sendDataJob = viewModelScope.launch(Dispatchers.IO) {
            val newMessage = Message(
                id = UUID.randomUUID().toString(),
                text = messageText,
                date = System.currentTimeMillis(),
                sender = sender,
                channel = channel
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