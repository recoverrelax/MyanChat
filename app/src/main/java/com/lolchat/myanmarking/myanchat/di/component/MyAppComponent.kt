package com.lolchat.myanmarking.myanchat.di.component

import android.app.Application
import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.di.module.*
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.other.DynamicUrlBuilder
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomPersistentDb
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotXmppService
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import com.lolchat.myanmarking.myanchat.ui.activity.EntryActivity
import com.lolchat.myanmarking.myanchat.ui.view_item.FriendView
import dagger.Component

@MyAppScope
@Component(modules = arrayOf(
        MyAppModule::class,
        StorageModule::class,
        XmppModule::class,
        NetworkModule::class
)
)
interface MyAppComponent{
    fun inject(app: MyApp)
    fun inject(service: RiotXmppService)
    fun inject(entryActivity: EntryActivity)

    fun inject(view: FriendView)

    // providers

    fun IXmppManager(): IXmppManager
    fun urlBuilder(): DynamicUrlBuilder

    fun app(): Application
    fun roomDb(): RoomPersistentDb
}