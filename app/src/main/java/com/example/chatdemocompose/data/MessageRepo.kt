package com.example.chatdemocompose.data

import com.example.chatdemocompose.domain.Message
import javax.inject.Inject

internal interface MessageRepo {
    suspend fun loadLocalMessages(): List<Message>

    suspend fun saveLocalMessage(content: Message)

    suspend fun clearLocalMessages()
}

internal class MessageRepoImpl @Inject constructor(
    private val localDataSource: LocalMessageDataSource
): MessageRepo {

    override suspend fun loadLocalMessages(): List<Message> {
        return localDataSource.fetchMessages()
    }

    override suspend fun saveLocalMessage(content: Message) {
        localDataSource.saveMessage(content)
    }

    override suspend fun clearLocalMessages() {
        localDataSource.deleteAll()
    }
}