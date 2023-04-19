package com.example.chatdemocompose.usecases

import com.example.chatdemocompose.data.MessageRepo
import com.example.chatdemocompose.domain.Message
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepo: MessageRepo
) {
    suspend operator fun invoke(content: Message){
        return messageRepo.saveLocalMessage(content)
    }
}