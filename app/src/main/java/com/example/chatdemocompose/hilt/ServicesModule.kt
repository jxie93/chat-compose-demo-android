package com.example.chatdemocompose.hilt

import com.example.chatdemocompose.data.ContentRepo
import com.example.chatdemocompose.data.ContentRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServicesModule {

    @Binds
    @Singleton
    abstract fun bindContentRepo(impl: ContentRepoImpl): ContentRepo

}