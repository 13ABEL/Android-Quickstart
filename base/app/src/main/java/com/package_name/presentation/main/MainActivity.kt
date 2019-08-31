package com.chatbotutsc.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.chatbotutsc.R
import com.chatbotutsc.data.auth.AuthManager
import com.chatbotutsc.domain.model.ChatUser
import com.chatbotutsc.presentation.base.ChatbotActivity
import com.chatbotutsc.presentation.chat.ChatFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import timber.log.Timber
import javax.inject.Inject

private const val RC_GET_AUTH_CODE = 9003
private const val FAILURE = 0

class MainActivity : ChatbotActivity() {
    @Inject lateinit var authManager : AuthManager
    @Inject lateinit var factory : MainVM.Factory

    private val mainVM : MainVM by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create() as T
            }
        }).get(MainVM::class.java)
    }

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainVM.onAuthenticationLiveData.nonNull().observe(this) { user ->
            handleOnAuthentication(user)
        }

        verifyOrSignIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_GET_AUTH_CODE -> {
                Timber.d("result code $resultCode")
                if (resultCode == FAILURE) {
                    // attempt sign in again
                    Toast.makeText(this, "You need to be authenticated to use this app", Toast.LENGTH_LONG).show()
                    GoogleSignIn.getClient(this, gso).apply {
                        startActivityForResult(signInIntent, RC_GET_AUTH_CODE)
                    }
                } else {
                    data?.let {
                        // parse the auth code and continue to main app
                        parseSignInResult(it)
                        mainVM.onAuthenticated()
                    }
                }
            }
        }
    }

    // checks if user already signed in, otherwise -> start sign in flow
    private fun verifyOrSignIn() {
        val currAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (currAccount == null) {
            GoogleSignIn.getClient(this, gso).apply {
                startActivityForResult(signInIntent, RC_GET_AUTH_CODE)
            }
        } else mainVM.onAuthenticated()
    }

    private fun parseSignInResult(signInData : Intent) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(signInData)
        try {
            val account = task.result
            authManager.storeServerAuthCode(account!!.serverAuthCode!!)
        } catch (e : ApiException) {
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_LONG).show()
        }
    }

    // region livedata handlers

    private fun handleOnAuthentication(chatUser: ChatUser) {
        val chatFragment = ChatFragment.create(chatUser.userId)

        // add the default view only if there are no additional fragments on the stack
        supportFragmentManager.apply {
            if (fragments.size < 1) {
                beginTransaction()
                    .replace(R.id.main_content_frame, chatFragment)
                    .commit()
            }
        }
    }

    // endregion livedata handlers
}
