package com.chatbotutsc.dagger

import android.app.Application
import android.content.Context
import com.chatbotutsc.dagger.modules.ActivityModule
import com.chatbotutsc.dagger.modules.AppModule
import com.chatbotutsc.dagger.modules.FragmentBuildersModule
import com.chatbotutsc.dagger.modules.RepoModule
import com.chatbotutsc.ChatbotApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentBuildersModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    /**
     * this is how we allow the component to build itself
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app : Application) : Builder

        @BindsInstance
        fun context(appContext : Context) : Builder

        fun build() : AppComponent
    }

    fun inject(app : ChatbotApp)
}
