package com.chatbotutsc.data.auth

import android.app.Application
import com.chatbotutsc.data.auth.AuthKeys.Companion.SERVER_AUTH_CODE
import de.adorsys.android.securestoragelibrary.SecurePreferences
import javax.inject.Inject

class AuthManagerImpl @Inject constructor(
    private val app : Application
): AuthManager {
    override fun storeServerAuthCode(serverAuthCode: String) {
        SecurePreferences.setValue(app, SERVER_AUTH_CODE, serverAuthCode)
    }

    override suspend fun retrieveApiToken() {
        val serverAuthCode = SecurePreferences.getStringValue(app, SERVER_AUTH_CODE, null)
            ?: throw AuthException.NoServerAuthCode()

        // TODO
    }

}