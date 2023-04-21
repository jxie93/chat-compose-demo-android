package com.example.chatdemocompose.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatdemocompose.R
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme

@Composable
fun AppAlert(
    text: String,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(text = text)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.btn_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.btn_cancel),
                    color = Color.Gray
                )
            }
        }
    )
}

@Preview
@Composable
fun AppAlertPreview() {
    ChatDemoComposeTheme(darkTheme = false) {
        AppAlert("Alert text goes here")
    }
}

@Preview
@Composable
fun AppAlertPreviewDark() {
    ChatDemoComposeTheme(darkTheme = true) {
        AppAlert("Alert text goes here")
    }
}