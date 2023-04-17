package com.example.chatdemocompose

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
import javax.inject.Inject

internal sealed class ListUiState {
    object Loading: ListUiState()
    data class Content(val data: List<Message>) : ListUiState()
}

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val getLocalMessagesUseCase: GetLocalMessagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var localDataJob: Job? = null

    fun loadLocalData() {
        localDataJob?.cancel()
        localDataJob = viewModelScope.launch(Dispatchers.IO) {
            //TODO

//            val contentData = getLocalListContentUseCase.invoke()
//            withContext(Dispatchers.Main) {
//                _uiState.emit(ListUiState.Content(contentData))
//            }
        }
    }

}