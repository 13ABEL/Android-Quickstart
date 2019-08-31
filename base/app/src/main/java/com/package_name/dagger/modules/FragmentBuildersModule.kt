package com.chatbotutsc.dagger.modules

import com.chatbotutsc.presentation.chat.ChatFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeChatFragment() : ChatFragment
}