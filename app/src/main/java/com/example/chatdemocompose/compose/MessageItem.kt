package com.example.chatdemocompose.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatdemocompose.domain.Message
import com.example.chatdemocompose.domain.Message.Companion.SENDER_ME
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    content: Message,
    showTimestamp: Boolean
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (showTimestamp) {
            MessageItemTimestamp(content = content)
        }
        MessageItemContent(
            content = content,
            showTimestamp = showTimestamp
        )
    }
}

@Composable
private fun MessageItemTimestamp(
    content: Message,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = content.timestamp,
            color = androidx.compose.material.MaterialTheme.colors.onSurface,
        )
    }
}

@Composable
private fun MessageItemContent(
    content: Message,
    showTimestamp: Boolean
) {
    val isReceived = content.isReceived
    val backgroundColor = if (isReceived) {
        androidx.compose.material.MaterialTheme.colors.primary
    } else {
        androidx.compose.material.MaterialTheme.colors.secondary
    }
    val backgroundShape = if (isReceived) {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp)
    }
    val padding = if (showTimestamp) {
        Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp)
    } else {
        Modifier.padding(8.dp, 2.dp, 8.dp, 2.dp)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isReceived) Arrangement.Start else Arrangement.End
    ) {
        Surface(
            color = backgroundColor,
            shape = backgroundShape,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .then(padding)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = content.text
            )
        }
    }
}

@Preview(widthDp = 320)
@Composable
fun MessageItemPreview() {
    ChatDemoComposeTheme {
        MessageItem(
            content = Message(
                id = "id",
                text = "Message text! Message text! Message text! Message text! Message text! Message text!",
                date = 1681822006700,
                sender = SENDER_ME
            ),
            showTimestamp = true
        )
    }
}

@Preview(widthDp = 320)
@Composable
fun MessageItemPreviewReceived() {
    ChatDemoComposeTheme {
        MessageItem(
            content = Message(
                id = "id",
                text = "Message text! Message text! Message text! Message text! Message text! Message text!",
                date = 1681822006700,
                sender = "sender"
            ),
            showTimestamp = true
        )
    }
}