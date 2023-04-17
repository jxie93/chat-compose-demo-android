package com.example.chatdemocompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatdemocompose.domain.DemoContent

@Dao
internal interface DemoContentDao {
    @Query("SELECT * FROM demoContent")
    fun getAll(): List<DemoContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(content: DemoContent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplaceAll(contents: List<DemoContent>)

    @Delete
    fun delete(content: DemoContent)

    @Query("DELETE FROM demoContent")
    fun deleteAll()
}