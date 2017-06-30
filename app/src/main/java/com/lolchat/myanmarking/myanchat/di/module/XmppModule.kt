package com.lolchat.myanmarking.myanchat.di.module

import android.app.Application
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.Riot.RiotApiConst
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsUser
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomPersistentDb
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotChatManager
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotConnManager
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotRosterManager
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import com.lolchat.myanmarking.myanchat.other.XmppImageHelperImpl
import dagger.Module
import dagger.Provides

@Module
class XmppModule {

    @Provides
    @MyAppScope
    fun provideXmppManager(
            appContext: Application,
            userPref: PrefsUser,
            rosterManager: RiotRosterManager,
            chatManager: RiotChatManager,
            connManager: RiotConnManager
    ): XmppManager = XmppManager(appContext, userPref, rosterManager, chatManager, connManager)

    @Provides
    @MyAppScope
    fun providesIXmppManager(xmppManager: XmppManager): IXmppManager = xmppManager

    @Provides
    @MyAppScope
    fun provideRosterManager(interactor: RoomInteractor): RiotRosterManager = RiotRosterManager(interactor)

    @Provides
    @MyAppScope
    fun provideChatManager(): RiotChatManager = RiotChatManager()

    @Provides
    @MyAppScope
    fun provideConnManager(): RiotConnManager = RiotConnManager()

    @Provides
    @MyAppScope
    fun provideImageHelper(riotApiConst: RiotApiConst): XmppImageHelperImpl = XmppImageHelperImpl(riotApiConst)
}