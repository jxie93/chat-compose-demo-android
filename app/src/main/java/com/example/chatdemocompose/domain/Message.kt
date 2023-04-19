package com.example.chatdemocompose.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import java.util.Date
import java.util.Locale
import java.util.UUID

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
    }

    val isReceived
        get() = sender != SENDER_ME

    private val dateTime
        get() = Date(date)

    val timestamp: String
        get() {
            val calendar = Calendar.getInstance().apply {
                time = dateTime
            }
            val weekDays = DateFormatSymbols(Locale.ROOT).weekdays
            return "${weekDays[calendar.get(DAY_OF_WEEK)]} ${calendar.get(HOUR_OF_DAY)}:${calendar.get(MINUTE)}"
        }

}