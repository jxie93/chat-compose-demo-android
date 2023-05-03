package com.example.chatdemocompose.usecases

import com.example.chatdemocompose.domain.Message

class MessageLastInBlockUseCase(
    private val messageShowTimestampUseCase: MessageShowTimestampUseCase
) {
    operator fun invoke(
        prevMessage2: Message?,
        prevMessage: Message?,
        currentMessage: Message,
        nextMessage: Message?
    ): Boolean {
        val nextSameSender = nextMessage?.sender != currentMessage.sender
        val prevSameSender = prevMessage?.sender != currentMessage.sender
        val currentShowTimestamp = messageShowTimestampUseCase.invoke(
            prevMessage = prevMessage,
            currentMessage = currentMessage,
            nextMessage = nextMessage
        )
        return prevMessage?.let {
            val prevShowTimestamp = messageShowTimestampUseCase.invoke(
                prevMessage = prevMessage2,
                currentMessage = prevMessage,
                nextMessage = currentMessage
            )
            prevShowTimestamp ||
                    currentShowTimestamp &&
                    nextSameSender && prevSameSender
        } ?: true
    }
}