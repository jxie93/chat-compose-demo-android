package com.example.shared.domain

import kotlinx.serialization.Serializable

@Serializable
data class Message (
    val id: String,
    val channel: String,
    val text: String,
    val date: Long,
    val sender: String,
) {
    companion object {
        const val SENDER_ME = "sender_me"
        const val MIN_TIME_DIFFERENCE_TIMESTAMP_MILLIS = 30 * 60 * 1000L
        const val CHANNEL_ALICE = "Alice"
        const val CHANNEL_BODHI = "Bodhi"
    }

}