package com.example.chatdemocompose.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalSerializationApi::class)
internal class EntityConverters {
    private val json = Json { isLenient = true }
    @TypeConverter
    fun fromStringToList(input: String): List<String> = json.decodeFromString(input)
    @TypeConverter
    fun fromList(list: List<String>): String = json.encodeToString(list)
}

@Entity
data class Message(
    @PrimaryKey val id: String,
    @ColumnInfo("channel") val channel: String,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("date") val date: Long,
    @ColumnInfo("sender") val sender: String,
) {

    companion object {
        const val SENDER_ME = "sender_me"
        const val MIN_TIME_DIFFERENCE_TIMESTAMP_MILLIS = 30 * 60 * 1000L
        const val CHANNEL_ALICE = "Alice"
        const val CHANNEL_BODHI = "Bodhi"
    }

    val isReceived
        get() = sender != SENDER_ME

    private val dateTime
        get() = Date(date)

    val timestamp: String
        get() = SimpleDateFormat("EEEE HH:mm", Locale.ROOT).format(dateTime)

}