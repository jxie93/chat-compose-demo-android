package com.example.chatdemocompose.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatdemocompose.R
import com.example.chatdemocompose.delegates.AppDrawerDelegate
import com.example.chatdemocompose.domain.Message.Companion.CHANNEL_ALICE
import com.example.chatdemocompose.domain.Message.Companion.CHANNEL_BODHI
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    delegate: AppDrawerDelegate = AppDrawerDelegate.previewDelegate,
    content: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    if (drawerState.isAnimationRunning) {
        focusManager.clearFocus() // force close keyboard
    }

    if (drawerState.isOpen) {
        BackHandler(onBack = {
            scope.launch {
                drawerState.close()
            }
        })
    }

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .background(MaterialTheme.colors.background)
            ) {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                DrawerTopHeader()
                Divider(color = Color.LightGray)
                ChannelsList(
                    channels = delegate.channels,
                    selectedChannel = delegate.selectedChannel,
                    onChannelSelected = { delegate.onChannelSelected(it) }
                )
                Divider(color = Color.LightGray)
                ToolsList(
                    onGenerateResponse = { delegate.onGenerateResponse(it) },
                    onClearChannel = { delegate.onClearChannel() }
                )
            }
        },
        content = content
    )
}

@Composable
fun ChannelsList(
    channels: List<String>,
    selectedChannel: String,
    onChannelSelected: (String) -> Unit = {},
) {
    Column {
        DrawerSectionHeader(stringResource(id = R.string.header_channels))
        channels.forEach {
            ChannelItem(
                title = it,
                isSelected = selectedChannel == it,
                onPressed = { onChannelSelected(it) }
            )
        }
    }
}

@Composable
fun ToolsList(
    onGenerateResponse: (Int) -> Unit,
    onClearChannel: () -> Unit
) {
    Column {
        DrawerSectionHeader(stringResource(id = R.string.header_tools))
        ToolItem(
            icon = R.drawable.ic_add_message,
            title = stringResource(id = R.string.btn_generate_response),
            onPressed = { onGenerateResponse(1) }
        )
        ToolItem(
            icon = R.drawable.ic_add_message,
            title = stringResource(id = R.string.btn_generate_response_x3),
            onPressed = { onGenerateResponse(3) }
        )
        ToolItem(
            icon = R.drawable.ic_delete,
            title = stringResource(id = R.string.btn_clear_channel),
            onPressed = onClearChannel
        )
    }
}

@Composable
fun DrawerTopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.widthIn(max = 64.dp),
            contentDescription = null,
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun DrawerSectionHeader(
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 16.dp, 16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.button,
            color = Color.Gray
        )
    }
}

@Composable
fun ToolItem(
    icon: Int,
    title: String,
    onPressed: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPressed() }
            .padding(24.dp, 16.dp, 16.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = MaterialTheme.colors.primary,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = title,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.button.copy(fontSize = 16.sp),
        )
    }
}

@Composable
fun ChannelItem(
    title: String,
    isSelected: Boolean,
    onPressed: () -> Unit = {}
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        Color.Transparent
    }
    val iconColor = if (isSelected) {
        Color.White
    } else {
        MaterialTheme.colors.primary
    }
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .clickable { onPressed() }
            .padding(24.dp, 16.dp, 16.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_contact),
            tint = iconColor,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = title,
            color = iconColor,
            style = MaterialTheme.typography.button.copy(fontSize = 16.sp),
        )
    }
}

@Preview
@Composable
fun AppDrawerPreview() {
    ChatDemoComposeTheme(darkTheme = false) {
        AppDrawer(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            delegate = AppDrawerDelegate.previewDelegate
        )
    }
}

@Preview
@Composable
fun AppDrawerPreviewDark() {
    ChatDemoComposeTheme(darkTheme = true) {
        AppDrawer(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            delegate = AppDrawerDelegate.previewDelegate
        )
    }
}
