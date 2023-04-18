package com.example.chatdemocompose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatdemocompose.compose.MessageInput
import com.example.chatdemocompose.compose.MessageItem
import com.example.chatdemocompose.domain.Message
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    uiState: ChatScreenUiState,
    onNavIconPressed: () -> Unit = { }
) {
    val scrollState = rememberLazyListState()
//    val topBarState = rememberTopAppBarState()
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ChatScreenTopBar(
                channelName = uiState.channelName,
                onNavIconPressed = onNavIconPressed
            )
        },
//        modifier = modifier
//            .nestedScroll(object: NestedScrollConnection{
//                //TODO?
//            })
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MessageList(
                modifier = Modifier
                    .weight(1f)
                    .background(color = androidx.compose.material.MaterialTheme.colors.background),
                messages = uiState.messages,
                scrollState = scrollState
            )
            MessageInput(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                onSendMessage = {
                    //TODO
                },
                onResetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                }
            )
        }
    }
}

@Composable
fun ChatScreenTopBar(
    modifier: Modifier = Modifier,
    channelName: String,
    onNavIconPressed: () -> Unit = { }
) {
    AppBar(
        modifier = modifier.statusBarsPadding(),
        onNavIconPressed = onNavIconPressed,
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = channelName)
            }
        }
    )
}

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    scrollState: LazyListState,
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        reverseLayout = true,
        state = scrollState,
        modifier = Modifier.fillMaxSize().then(modifier)
    ) {
        for (index in messages.indices) {
            val prevMessage = messages.getOrNull(index - 1)
            val nextMessage = messages.getOrNull(index + 1)
            val currentMessage = messages[index]

            val shouldShowTimestamp = nextMessage?.sender != currentMessage.sender

            item {
                MessageItem(
                    content = currentMessage,
                    showTimestamp = shouldShowTimestamp
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatDemoComposeTheme {
        ChatScreen(
            uiState = ChatScreenUiState(
                channelName = "Sarah",
                initialMessages = DummyFactory.generateMessages(10)
            ),
        )
    }
}