package com.chatbotutsc.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.chatbotutsc.R
import com.chatbotutsc.domain.model.ChatMessage
import com.chatbotutsc.presentation.base.ChatbotFragment
import com.shopify.livedataktx.observe
import com.stfalcon.chatkit.messages.MessagesListAdapter
import javax.inject.Inject

class ChatFragment private constructor(): ChatbotFragment() {
    @Inject
    lateinit var factory : ChatVM.Factory

    lateinit var userId : String

    lateinit var messagesAdapter : MessagesListAdapter<ChatMessage>

    private val displaySubVM: ChatVM by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create() as T
            }
        }).get(ChatVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            userId = getString(ARG_USER_ID)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesAdapter = MessagesListAdapter(userId, null)
    }

    override fun bindViewModel(lifecycleOwner: LifecycleOwner) {
        displaySubVM.messagesLiveData.observe(lifecycleOwner) { newMsg ->
            messagesAdapter.addToStart(newMsg, false)
        }
    }

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun create(user_id : String) : ChatFragment {
            return ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_ID, user_id)
                }
            }
        }
    }
}