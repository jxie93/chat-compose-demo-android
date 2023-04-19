package com.example.chatdemocompose.composables

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
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatdemocompose.R
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme

@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    onSendMessage: (String) -> Unit = {},
    onResetScroll: () -> Unit = {}
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
                .padding(horizontal = 8.dp, vertical = 8.dp),
            ) {

            // text input
            MessageTextInput(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onValueChange = { textState = it },
                onFocused = onResetScroll,
                textState = textState
            )

            Spacer(modifier = Modifier.width(8.dp))

            // send button
            Button(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .height(IntrinsicSize.Min)
                    .widthIn(80.dp)
                    .clip(RoundedCornerShape(50)),
                enabled = textState.text.isNotEmpty(),
                content = {
                  Text(text = stringResource(id = R.string.btn_send))
                },
                onClick = {
                    onSendMessage(textState.text)
                    textState = TextFieldValue()
                }
            )
        }
    }
}

@Composable
fun MessageTextInput(
    modifier: Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    onFocused: () -> Unit,
    textState: TextFieldValue
) {

//    var inputText by remember { mutableStateOf(TextFieldValue()) }

    Surface(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(24.dp)
            ),
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            BasicTextField(
                value = textState,
                onValueChange = {
                    onValueChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .onFocusChanged { state ->
                        if (state.isFocused) onFocused()
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType =  KeyboardType.Text,
                )
            )

            // hint
            if (textState.text.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    text = stringResource(id = R.string.text_field_hint),
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview
@Composable
fun MessageInputPreview() {
    ChatDemoComposeTheme {
        MessageInput()
    }
}