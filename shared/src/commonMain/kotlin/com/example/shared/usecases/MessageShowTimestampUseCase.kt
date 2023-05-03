package com.example.shared.usecases

import com.example.shared.domain.Message
import com.example.shared.domain.Message.Companion.MIN_TIME_DIFFERENCE_TIMESTAMP_MILLIS
import kotlin.math.abs

class MessageShowTimestampUseCase() {
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