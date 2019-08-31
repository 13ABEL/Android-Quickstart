package com.chatbotutsc.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.chatbotutsc.dagger.ChatbotInjectable
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

abstract class ChatbotFragment : Fragment(), ChatbotInjectable {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel(viewLifecycleOwner)
    }

    protected open fun bindViewModel(lifecycleOwner : LifecycleOwner) { }

    protected fun dismiss() {
        childFragmentManager.popBackStackImmediate()
    }
}