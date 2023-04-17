package com.example.chatdemocompose.domain

internal interface ContentDataSource {

    suspend fun fetchListContent(): List<DemoContent>

}