package com.example.chatdemocompose.data

import com.example.chatdemocompose.domain.MessageDataSource
import com.example.chatdemocompose.domain.Message
import javax.inject.Inject

class LocalMessageDataSource @Inject constructor(
    private val dao: MessagesDao
): MessageDataSource {
    override suspend fun fetchMessages(): List<Message> {
        return dao.getAll()
    }

    override suspend fun saveMessage(content: Message) {
        dao.insertAndReplace(content)
    }

    fun saveMessages(content: List<Message>) {
        dao.insertAndReplaceAll(content)
    }

    override suspend fun deleteMessage(content: Message) {
        dao.delete(content)
    }

    override suspend fun deleteMessage(id: String) {
        dao.deleteMessage(id)
    }

    override suspend fun deleteChannel(channel: String) {
        dao.deleteChannel(channel)
    }

    fun deleteAll() {
        dao.deleteAll()
    }
}