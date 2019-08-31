package com.chatbotutsc.domain

import java.lang.Exception

abstract class ChatbotException(
    message : String,
    cause : Throwable?
) : Exception(message, cause)