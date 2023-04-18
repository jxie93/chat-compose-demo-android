package com.example.chatdemocompose.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatdemocompose.R
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme

@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    onSendMessage: (String) -> Unit,
    onResetScroll: () -> Unit
) {

    var isKeyboardVisible by remember { mutableStateOf(false) }
    val dismissKeyboard = { isKeyboardVisible = false }

    // intercept back navigation when keyboard is open
    if (isKeyboardVisible) {
        BackHandler(onBack = dismissKeyboard)
    }

    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Surface {
        Row(
            modifier = Modifier
                .then(modifier)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 64.dp)
                .height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {

            // text input
            MessageTextInput(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onValueChange = { textState = it },
                onFocused = onResetScroll
            )

            Spacer(modifier = Modifier.width(8.dp))

            // send button
            Button(
                modifier = Modifier
                    .widthIn(64.dp)
                    .fillMaxHeight(),
                enabled = textState.text.isNotEmpty(),
                content = {
                  Text(text = stringResource(id = R.string.btn_send))
                },
                onClick = { onSendMessage(textState.text) }
            )
        }
    }
}

@Composable
fun MessageTextInput(
    modifier: Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    onFocused: () -> Unit
) {

    var inputText by remember { mutableStateOf(TextFieldValue()) }

    Surface(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(percent = 50)
            ),
    ) {
        Box {
            BasicTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    onValueChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterStart)
                    .onFocusChanged { state ->
                        if (state.isFocused) onFocused()
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType =  KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                maxLines = 1,
                cursorBrush = SolidColor(LocalContentColor.current),
                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
            )

            // hint
            if (inputText.text.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    text = stringResource(id = R.string.textfield_hint),
                )
            }
        }
    }
}

@Preview
@Composable
fun MessageInputPreview() {
    ChatDemoComposeTheme {
        MessageInput(
            onSendMessage = {},
            onResetScroll = {}
        )
    }
}