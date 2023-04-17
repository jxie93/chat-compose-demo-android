package com.example.chatdemocompose.data

import android.util.Log
import com.example.chatdemocompose.domain.DemoContent
import javax.inject.Inject

internal interface ContentRepo {
    suspend fun loadLocalListContent(): List<DemoContent>
}

internal class ContentRepoImpl @Inject constructor(
    private val localDataSource: LocalContentDataSource
): ContentRepo {

    override suspend fun loadLocalListContent(): List<DemoContent> {
        return localDataSource.fetchListContent()
    }

}