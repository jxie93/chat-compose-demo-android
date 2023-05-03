package com.example.chatdemocompose.delegates

interface ChatScreenDelegate {
    fun getChannel(): String
    fun onNavIconPressed()
    fun onSendMessage(messageText: String)
}