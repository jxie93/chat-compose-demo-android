package com.example.chatdemocompose

import com.example.chatdemocompose.domain.Message
import com.example.chatdemocompose.domain.Message.Companion.SENDER_ME
import java.util.UUID
import kotlin.random.Random

class DummyFactory {
    companion object {
        fun generateReceivedMessagesOnly(count: Int): List<Message> {
            return List(count) {
                val id = UUID.randomUUID().toString()
                Message(
                    id = id,
                    text = "Received message id:$id",
                    date = 1681822006700 + (it - count) * -10000,
                    sender = "dummy",
                    channel = "Bob"
                )
            }
        }

        fun generateMessages(count: Int): List<Message> {
            return List(count) {
                val id = UUID.randomUUID().toString()
                val isReceived = Random.nextBoolean()
                Message(
                    id = id,
                    text = "${if (isReceived) "Received message" else "Message"} id:$id",
                    date = 1681822006700 + (it - count) * -10000,
                    sender = if (isReceived) "dummy" else SENDER_ME,
                    channel = "Bob"
                )
            }
        }
    }
}