package com.example.chatdemocompose.usecases

import com.example.chatdemocompose.data.MessageRepo
import javax.inject.Inject

class RemoveLocalMessagesUseCase @Inject constructor(
    private val messageRepo: MessageRepo
) {
    suspend operator fun invoke(channel: String) {
        return messageRepo.clearLocalMessages(channel)
    }
}