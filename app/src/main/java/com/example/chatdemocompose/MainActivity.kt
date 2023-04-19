package com.example.chatdemocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.chatdemocompose.composables.AppDrawer
import com.example.chatdemocompose.composables.ChatScreen
import com.example.chatdemocompose.domain.Message.Companion.CHANNEL_ALICE
import com.example.chatdemocompose.domain.Message.Companion.CHANNEL_BODHI
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val chatViewModel: ChatViewModel by viewModels()

    private val initialChannel = CHANNEL_ALICE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ChatDemoComposeTheme {
                App(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = chatViewModel,
                    initialChannel = initialChannel
                )
            }
        }

        // load initial channel
        chatViewModel.loadMessages(initialChannel)
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
    initialChannel: String
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentChannel by remember { mutableStateOf(initialChannel) }

    AppDrawer(
        drawerState = drawerState,
        content = {
            Surface(
                modifier = modifier,
                color = MaterialTheme.colors.background
            ) {
                ChatScreen(
                    channel = currentChannel,
                    uiState = uiState,
                    onSendMessage = { msg ->
                        viewModel.sendMessage(msg, currentChannel)
                    },
                    onNavIconPressed = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        },
        selectedChannel = currentChannel,
        onChannelSelected = {
            scope.launch {
                drawerState.close()
            }
            currentChannel = it
            viewModel.loadMessages(it)
        },
        channels = listOf(
            CHANNEL_ALICE,
            CHANNEL_BODHI
        )
    )
}