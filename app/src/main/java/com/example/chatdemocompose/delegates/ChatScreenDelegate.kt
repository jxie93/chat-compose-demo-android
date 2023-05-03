package com.example.chatdemocompose.delegates

interface ChatScreenDelegate {
    val channel: String
    fun onNavIconPressed()
    fun onSendMessage(messageText: String)
}