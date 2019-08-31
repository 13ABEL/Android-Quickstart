package com.chatbotutsc.data.auth

import com.chatbotutsc.domain.ChatbotException

interface AuthManager {
    fun storeServerAuthCode(serverAuthCode : String)

    // retrieves api token for server using auth code
    suspend fun retrieveApiToken()
}

sealed class AuthException(
    message : String,
    cause : Throwable? = null
) : ChatbotException(message, cause) {
    class NoServerAuthCode: AuthException("There is no auth code stored for the server")
}

class AuthKeys {
    companion object {
        const val SERVER_AUTH_CODE = "server_auth_keys"
    }
}
