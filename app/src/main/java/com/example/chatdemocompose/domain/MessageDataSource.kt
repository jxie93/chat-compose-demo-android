package com.example.chatdemocompose.domain

internal interface MessageDataSource {

    suspend fun fetchMessages(): List<Message>

    suspend fun saveMessage(content: Message)

    suspend fun deleteMessage(content: Message)

    suspend fun deleteMessage(id: String)

    suspend fun deleteChannel(channel: String)

}