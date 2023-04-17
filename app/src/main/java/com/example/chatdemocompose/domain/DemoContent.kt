package com.example.chatdemocompose.domain

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
internal class EntityConverters {
    private val json = Json { isLenient = true }
    @TypeConverter
    fun fromStringToList(input: String): List<String> = json.decodeFromString(input)
    @TypeConverter
    fun fromList(list: List<String>): String = json.encodeToString(list)
    @TypeConverter
    fun fromUri(uri: Uri): String = uri.toString()
}

@Entity
internal data class DemoContent(
    @PrimaryKey val id: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("isPlaceholder") val isPlaceholder: Boolean
) {
    companion object {
        fun getPlaceholders(count: Int) = List(count) {
            DemoContent(
                id = UUID.randomUUID().toString(),
                title = "",
                description = "",
                isPlaceholder = true
            )
        }
    }

}