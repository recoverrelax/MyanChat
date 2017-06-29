package com.lolchat.myanmarking.myanchat.di.component

import android.app.Application
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.di.module.*
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.other.DynamicUrlBuilder
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomPersistentDb
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotXmppService
import com.lolchat.myanmarking.myanchat.ui.activity.MainActivity
import com.lolchat.myanmarking.myanchat.ui.view_item.FriendView
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule

@MyAppScope
@Component(modules = arrayOf(
        MyAppComponent.MyAppModule::class,
        AndroidInjectionModule::class,
        StorageModule::class,
        XmppModule::class,
        NetworkModule::class,
        ActivityBindModule::class
)
)
interface MyAppComponent {
    fun inject(app: MyApp)
    fun inject(service: RiotXmppService)

    fun inject(view: FriendView)
    /*fun inject(mainActivity: MainActivity)

    // providers

    fun IXmppManager(): IXmppManager
    fun urlBuilder(): DynamicUrlBuilder

    fun app(): Application
    fun roomDb(): RoomPersistentDb*/

    @Module
    class MyAppModule(
            val context: MyApp
    ) {

        @Provides
        @MyAppScope
        fun provideApplication(): Application {
            return context
        }

        @Provides
        @MyAppScope
        fun provideDaggerApplication(): MyApp {
            return context
        }
    }
}