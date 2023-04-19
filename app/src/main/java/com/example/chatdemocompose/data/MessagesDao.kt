package com.example.chatdemocompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatdemocompose.domain.Message

@Dao
interface MessagesDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(content: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplaceAll(contents: List<Message>)

    @Delete
    fun delete(content: Message)

    @Query("DELETE FROM message WHERE channel = :channel")
    fun deleteChannel(channel: String)

    @Query("DELETE FROM message WHERE id = :id")
    fun deleteMessage(id: String)

    @Query("DELETE FROM message")
    fun deleteAll()
}