package com.chatbotutsc.dagger.modules

import com.chatbotutsc.data.auth.AuthManager
import com.chatbotutsc.data.auth.AuthManagerImpl
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class AppModule {
    @Binds
    abstract fun provideAuthManager(impl: AuthManagerImpl) : AuthManager
}