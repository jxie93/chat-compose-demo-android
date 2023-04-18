package com.example.chatdemocompose

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatdemocompose.domain.Message
import com.example.chatdemocompose.usecases.GetLocalMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatScreenUiState(
    val channelName: String,
    initialMessages: List<Message>
) {

    companion object {
        val EMPTY = ChatScreenUiState("", emptyList())
    }

    private val _messages: MutableList<Message> = initialMessages.toMutableStateList()
    val messages: List<Message> = _messages

    fun addMessage(content: Message) {
        _messages.add(0, content)
    }
}

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getLocalMessagesUseCase: GetLocalMessagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatScreenUiState.EMPTY)
    val uiState = _uiState.asStateFlow()

    private var localDataJob: Job? = null

    fun loadLocalData() {
        localDataJob?.cancel()
        localDataJob = viewModelScope.launch(Dispatchers.IO) {
            //TODO test

//            val contentData = getLocalListContentUseCase.invoke()
            withContext(Dispatchers.Main) {
//                _uiState.emit(ChatScreenUiState())
                _uiState.emit(ChatScreenUiState("Sarah", DummyFactory.generateMessages(10)))
            }
        }
    }

}