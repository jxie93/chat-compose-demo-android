package com.example.chatdemocompose.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatdemocompose.ui.theme.ChatDemoComposeTheme

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {

    TopAppBar(
        modifier = modifier,
        actions = actions,
        title = title,
        navigationIcon = {
            AppBarIcon(
                modifier = Modifier
                    .size(64.dp)
                    .clickable(onClick = onNavIconPressed)
                    .padding(16.dp)
            )
        }
    )
}

@Composable
fun AppBarIcon(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = android.R.drawable.ic_menu_more),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun AppBarPreview() {
    ChatDemoComposeTheme(darkTheme = false) {
        AppBar(title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Title")
            }
        })
    }
}

@Preview
@Composable
fun AppBarPreviewDark() {
    ChatDemoComposeTheme(darkTheme = true) {
        AppBar(title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Title")
            }
        })
    }
}