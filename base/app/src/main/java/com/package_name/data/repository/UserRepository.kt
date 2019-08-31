package com.chatbotutsc.data.repository

import com.chatbotutsc.domain.model.ChatUser

interface UserRepository {
    suspend fun getSelf() : ChatUser
}