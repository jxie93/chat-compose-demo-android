package com.example.chatdemocompose.data

import com.example.chatdemocompose.domain.Message
import javax.inject.Inject

interface MessageRepo {
    suspend fun loadLocalMessages(): List<Message>

    suspend fun saveLocalMessage(content: Message)

    suspend fun clearAllLocalMessages()

    suspend fun clearLocalMessages(channel: String)
}

class MessageRepoImpl @Inject constructor(
    private val localDataSource: LocalMessageDataSource
): MessageRepo {

    override suspend fun loadLocalMessages(): List<Message> {
        return localDataSource.fetchMessages()
    }

    override suspend fun saveLocalMessage(content: Message) {
        localDataSource.saveMessage(content)
    }

    override suspend fun clearAllLocalMessages() {
        localDataSource.deleteAll()
    }

    override suspend fun clearLocalMessages(channel: String) {
        localDataSource.deleteChannel(channel)
    }
}