package com.chatbotutsc.domain.model

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

abstract class ChatMessage : IMessage {
    // ids of the sender and recipientId
    abstract val senderId  : String
    abstract val recipientId : String
    abstract val messageId : String

    abstract val created : Date

    override fun getId(): String = messageId
    override fun getCreatedAt(): Date = created
    override fun getUser(): IUser? = null
}

class StringMessage(
    override val senderId: String,
    override val recipientId: String,
    override val messageId: String,
    override val created: Date,
    val message : String
) : ChatMessage() {

    override fun getText(): String? = message
}