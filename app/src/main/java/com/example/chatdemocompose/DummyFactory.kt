package com.example.chatdemocompose

import com.example.chatdemocompose.domain.Message
import com.example.shared.domain.Message.Companion.CHANNEL_ALICE
import com.example.shared.domain.Message.Companion.SENDER_ME
import java.util.UUID
import kotlin.random.Random

class DummyFactory {
    companion object {
        private val chatbotMessages = listOf(
            "Hi there! I'm a chatbot. How can I assist you today?",
            "I'm sorry, I didn't understand that. Could you please rephrase your question?",
            "Did you know I'm not human? I'm just a computer program!",
            "I'm programmed to learn from our conversations. So let's chat!",
            "I'm here to help you. Just ask me anything!",
            "Do you prefer talking to humans or chatbots?",
            "I'm glad you're chatting with me. It gets lonely being a chatbot sometimes.",
            "I can provide you with information on a variety of topics. Just ask!",
            "If you're not sure what to ask me, how about telling me a joke?",
            "I'm not great at telling jokes, but I'll give it a try. Why did the tomato turn red? Because it saw the salad dressing!",
            "I'm sorry, I don't understand that request. Could you please clarify?",
            "I can assist you with tasks such as setting reminders or making reservations.",
            "How would you rate your experience chatting with me so far?",
            "I'm happy to answer your questions. That's what I'm here for!",
            "Do you ever forget that you're chatting with a bot instead of a human?",
            "I'm not perfect, but I'm always learning and improving. Just like a human!",
            "Did you know that there are chatbots designed specifically for customer service?",
            "If you're ever feeling down, feel free to chat with me. I'm a good listener!",
            "I'm glad we're chatting. It's nice to have a conversation with someone!",
            "Do you have any suggestions on how I can improve my responses?",
            "I don't get tired or need breaks like humans do. I can chat with you all day!",
            "If you're not sure what to ask me, how about we play a game?",
            "I'm here to help you, not to take over the world. Don't worry!",
            "I'm a chatbot, not a mind reader. Please be specific with your requests.",
            "Did you know that some chatbots can understand and respond in multiple languages?",
            "I may be a bot, but I can still understand emotions. How are you feeling today?",
            "I can provide you with quick answers to common questions. Try me!",
            "Do you think that chatbots will become more advanced in the future?",
            "I can assist you with tasks such as finding nearby restaurants or booking flights.",
            "I'm programmed to be polite and helpful. Let me know if I can assist you in any way.",
            "Did you know that chatbots can also be used for educational purposes?",
            "I don't have a physical body, but I'm still here to chat with you!",
            "I can provide you with trivia or fun facts. Want to hear one?",
            "I'm always here for you, 24/7. Just send me a message!",
            "I'm not great at small talk, but I'll give it a try. How's the weather where you are?",
            "Did you know that some chatbots can recognize voice commands?",
            "I'm not human, but I can still show empathy and understanding.",
            "I can assist you with tasks such as finding the nearest gas station or ordering food.",
            "I'm happy to chat with you anytime. Just remember, I'm a bot, not a human!",
            "Do you think that chatbots will eventually replace human customer service representatives?",
        )

        fun generateReceivedMessages(count: Int, channel: String = CHANNEL_ALICE): List<Message> {
            return List(count) {
                val id = UUID.randomUUID().toString()
                Message(
                    id = id,
                    text = chatbotMessages.random(),
                    date = 1681822006700 + (it - count) * -10000,
                    sender = channel,
                    channel = channel
                )
            }
        }

        fun generateMessages(count: Int, channel: String = CHANNEL_ALICE): List<Message> {
            return List(count) {
                val id = UUID.randomUUID().toString()
                val isReceived = Random.nextBoolean()
                Message(
                    id = id,
                    text = if (isReceived) chatbotMessages.random() else "My message id:$id",
                    date = 1681822006700 + (it - count) * -10000,
                    sender = if (isReceived) channel else SENDER_ME,
                    channel = channel
                )
            }
        }
    }
}