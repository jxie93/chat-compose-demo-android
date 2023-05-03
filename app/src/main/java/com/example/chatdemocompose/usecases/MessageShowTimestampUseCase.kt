package com.example.chatdemocompose.usecases

import com.example.chatdemocompose.domain.Message
import com.example.shared.domain.Message.Companion.MIN_TIME_DIFFERENCE_TIMESTAMP_MILLIS
import javax.inject.Inject
import kotlin.math.abs

class MessageShowTimestampUseCase @Inject constructor(

) {
    operator fun invoke(
        prevMessage: Message?,
        currentMessage: Message,
        nextMessage: Message?
    ): Boolean {
        val differentSender = nextMessage?.sender != currentMessage.sender
        val largeTimeDifference = nextMessage?.let {
            abs(it.date - currentMessage.date) > MIN_TIME_DIFFERENCE_TIMESTAMP_MILLIS
        } ?: false

        return differentSender || largeTimeDifference
    }
}