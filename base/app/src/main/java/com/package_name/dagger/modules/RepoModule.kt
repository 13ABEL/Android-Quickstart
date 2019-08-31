package com.chatbotutsc.dagger.modules

import com.chatbotutsc.data.repository.MessageRepository
import com.chatbotutsc.data.repository.MessageRepositoryImpl
import com.chatbotutsc.data.repository.UserRepository
import com.chatbotutsc.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class RepoModule {

    @Binds
    abstract fun bindMessageRepo(impl: MessageRepositoryImpl) : MessageRepository

    @Binds
    abstract fun bindUserRepo(impl: UserRepositoryImpl) : UserRepository
}