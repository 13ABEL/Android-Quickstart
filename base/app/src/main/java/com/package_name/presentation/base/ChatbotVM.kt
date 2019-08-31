package com.chatbotutsc.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class ChatbotVM : ViewModel(), CoroutineScope {
    private val tag : String = javaClass.toString().split(".").last().toUpperCase()

    override val coroutineContext = Dispatchers.Main + CoroutineName(tag) + SupervisorJob() + CoroutineExceptionHandler { context, e ->
        handleException(context, e)
        Log.e(tag, "caught exception", e)
    }

    /**
     * log exceptions by default -> allow vms to override and handle as necessary
     */
    protected fun handleException(context: CoroutineContext, e : Throwable) {
        Timber.e(e)
    }
}