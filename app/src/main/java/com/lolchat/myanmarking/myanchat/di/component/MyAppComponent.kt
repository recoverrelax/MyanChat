package com.lolchat.myanmarking.myanchat.di.component

import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.di.module.MyAppModule
import com.lolchat.myanmarking.myanchat.di.module.PrefsModule
import com.lolchat.myanmarking.myanchat.di.module.XmppModule
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.network.xmpp.RiotXmppService
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import dagger.Component

@MyAppScope
@Component(modules = arrayOf(
        MyAppModule::class,
        PrefsModule::class,
        XmppModule::class
)
)
interface MyAppComponent{
    fun inject(app: MyApp)
    fun inject(service: RiotXmppService)

    fun IXmppManager(): IXmppManager
}