package com.example.chatdemocompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chatdemocompose.domain.Message
import com.example.chatdemocompose.domain.EntityConverters

@Database(entities = [Message::class], version = 1, exportSchema = false)
@TypeConverters(EntityConverters::class)
internal abstract class AppDatabase: RoomDatabase() {
    abstract fun messagesDao(): MessagesDao
}