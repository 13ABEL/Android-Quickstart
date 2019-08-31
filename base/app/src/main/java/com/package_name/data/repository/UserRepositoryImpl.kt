package com.chatbotutsc.data.repository

import com.chatbotutsc.domain.model.ChatUser
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(): UserRepository {

    override suspend fun getSelf() : ChatUser {
        // TODO
        return ChatUser("test_id")
    }

}