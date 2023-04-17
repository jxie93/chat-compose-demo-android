package com.example.chatdemocompose.hilt

import com.example.chatdemocompose.data.MessageRepo
import com.example.chatdemocompose.data.MessageRepoImpl
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
    abstract fun bindContentRepo(impl: MessageRepoImpl): MessageRepo

}