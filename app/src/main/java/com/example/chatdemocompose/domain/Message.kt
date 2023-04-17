package com.example.chatdemocompose.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import java.util.Date
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
internal data class Message(
    @PrimaryKey val id: String,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("date") val date: Long,
) {
    val dateTime
        get() = Date(date)

    val dateTimeFormatted: String
        get() {
            val calendar = Calendar.getInstance().apply {
                time = dateTime
            }
            return "${calendar.get(DAY_OF_WEEK)} ${calendar.get(HOUR_OF_DAY)}:${calendar.get(MINUTE)}"
        }

    companion object {
        fun getPlaceholders(count: Int) = List(count) {
            Message(
                id = UUID.randomUUID().toString(),
                text = "",
                date = -1,
            )
        }
    }

}