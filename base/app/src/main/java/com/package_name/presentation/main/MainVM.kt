package com.chatbotutsc.presentation.main

import androidx.lifecycle.LiveData
import com.chatbotutsc.data.auth.AuthManager
import com.chatbotutsc.data.repository.MessageRepository
import com.chatbotutsc.data.repository.UserRepository
import com.chatbotutsc.domain.model.ChatUser
import com.chatbotutsc.presentation.base.ChatbotVM
import com.shopify.livedataktx.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainVM(
    private val authManager: AuthManager,
    private val messageRepo: MessageRepository,
    private val userRepo: UserRepository
) : ChatbotVM() {
    private val _onAuthenticationLiveData = SingleLiveData<ChatUser>()

    val onAuthenticationLiveData : LiveData<ChatUser> = _onAuthenticationLiveData

    fun onAuthenticated() {
        launch (Dispatchers.Main) {
            val user = userRepo.getSelf()
            _onAuthenticationLiveData.postValue(user)
        }
    }

    class Factory @Inject constructor(
        private val authManager: AuthManager,
        private val messageRepo: MessageRepository,
        private val userRepo: UserRepository
    ) {
        fun create () : MainVM {
            return MainVM(authManager, messageRepo, userRepo)
        }
    }
}