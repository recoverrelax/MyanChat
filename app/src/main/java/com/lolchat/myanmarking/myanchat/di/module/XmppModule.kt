package com.lolchat.myanmarking.myanchat.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.prefs.PrefsUser
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotChatManager
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotConnManager
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotRosterManager
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
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
    fun proviesIXmppManager(xmppManager: XmppManager): IXmppManager = xmppManager

    @Provides
    @MyAppScope
    fun provideRosterManager(): RiotRosterManager = RiotRosterManager()

    @Provides
    @MyAppScope
    fun provideChatManager(): RiotChatManager = RiotChatManager()

    @Provides
    @MyAppScope
    fun provideConnManager(): RiotConnManager = RiotConnManager()
}