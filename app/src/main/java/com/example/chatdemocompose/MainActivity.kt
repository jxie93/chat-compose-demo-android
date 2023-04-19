package com.example.chatdemocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.chatdemocompose.composables.ChatScreen
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ChatDemoComposeTheme {
                App(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = chatViewModel
                )
            }
        }

        chatViewModel.loadLocalData()
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        ChatScreen(
            uiState = uiState,
            onSendMessage = { msg ->
                viewModel.sendMessage(msg)
            }
        )
    }
}