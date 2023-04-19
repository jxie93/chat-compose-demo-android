package com.example.chatdemocompose.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            color = androidx.compose.material.MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
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
        androidx.compose.material.MaterialTheme.colors.primaryVariant
    } else {
        androidx.compose.material.MaterialTheme.colors.secondaryVariant
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
        if (!isReceived) Spacer(modifier = Modifier.fillMaxWidth(0.2f).weight(0.2f))
        Surface(
            color = backgroundColor,
            shape = backgroundShape,
            modifier = Modifier
                .weight(0.8f, false)
                .then(padding)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = content.text,
                color = Color.White
            )
        }
        if (isReceived) Spacer(modifier = Modifier.fillMaxWidth(0.2f).weight(0.2f))
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
                sender = SENDER_ME,
                channel = "channel"
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
                sender = "sender",
                channel = "channel"
            ),
            showTimestamp = true
        )
    }
}