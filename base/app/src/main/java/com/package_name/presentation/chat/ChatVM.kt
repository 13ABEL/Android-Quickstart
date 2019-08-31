package com.chatbotutsc.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatbotutsc.data.auth.AuthManager
import com.chatbotutsc.data.repository.MessageRepository
import com.chatbotutsc.data.repository.UserRepository
import com.chatbotutsc.domain.model.ChatMessage
import com.chatbotutsc.domain.model.ChatUser
import com.chatbotutsc.presentation.base.ChatbotVM
import com.shopify.livedataktx.SingleLiveData
import javax.inject.Inject

class ChatVM (
    private val authManager: AuthManager,
    private val messageRepo: MessageRepository,
    private val userRepo: UserRepository
) : ChatbotVM() {

    private val _currentUserLiveData = MutableLiveData<ChatUser>()
    private val _newMessageLiveData = SingleLiveData<ChatMessage>()

    val currentUserLiveData : LiveData<ChatUser> = _currentUserLiveData
    val messagesLiveData : LiveData<ChatMessage> = _newMessageLiveData

    init {
//        _currentUserLiveData.postValue()
    }

    class Factory @Inject constructor(
        private val authManager: AuthManager,
        private val messageRepo: MessageRepository,
        private val userRepo: UserRepository
    ) {
        fun create () : ChatVM {
            return ChatVM(authManager, messageRepo, userRepo)
        }
    }
}

