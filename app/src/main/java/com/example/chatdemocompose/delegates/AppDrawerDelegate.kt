package com.example.chatdemocompose.delegates

import com.example.shared.domain.Message.Companion.CHANNEL_ALICE
import com.example.shared.domain.Message.Companion.CHANNEL_BODHI

interface AppDrawerDelegate {
    val selectedChannel: String
    val channels: List<String>
    fun onChannelSelected(channelId: String)
    fun onGenerateResponse(count: Int)
    fun onClearChannel()

    companion object {
        val previewDelegate = object: AppDrawerDelegate {
            override val selectedChannel: String
                get() = CHANNEL_ALICE
            override val channels: List<String>
                get() = listOf(
                    CHANNEL_ALICE,
                    CHANNEL_BODHI
                )
            override fun onChannelSelected(channelId: String) = Unit
            override fun onGenerateResponse(count: Int) = Unit
            override fun onClearChannel() = Unit
        }
    }
}