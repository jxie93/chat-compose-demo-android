package com.example.chatdemocompose.usecases

import com.example.chatdemocompose.data.MessageRepo
import com.example.chatdemocompose.domain.Message
import javax.inject.Inject

class GetLocalMessagesUseCase @Inject constructor(
    private val messageRepo: MessageRepo
) {
    suspend operator fun invoke(): List<Message> {
        return messageRepo.loadLocalMessages().sortedByDescending { it.date }
    }
}