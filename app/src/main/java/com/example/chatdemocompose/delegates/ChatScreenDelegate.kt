package com.example.chatdemocompose.delegates

import com.example.chatdemocompose.domain.Message.Companion.CHANNEL_ALICE

interface ChatScreenDelegate {
    val channel: String
    fun onNavIconPressed()
    fun onSendMessage(messageText: String)
    companion object {
        val previewDelegate = object: ChatScreenDelegate {
            override val channel: String
                get() = CHANNEL_ALICE

            override fun onNavIconPressed() = Unit
            override fun onSendMessage(messageText: String) = Unit
        }
    }
}