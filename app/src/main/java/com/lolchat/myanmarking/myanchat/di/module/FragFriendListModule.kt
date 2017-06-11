package com.lolchat.myanmarking.myanchat.di.module

import android.app.Application
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lolchat.myanmarking.myanchat.di.scopes.FragmentScope
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomPersistentDb
import com.lolchat.myanmarking.myanchat.network.xmpp.XmppManager
import com.lolchat.myanmarking.myanchat.ui.adapter.FragFriendListAdapter
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendList
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendListManager
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendListPresenter
import dagger.Module
import dagger.Provides

@Module
class FragFriendListModule(
        val frag: FragFriendList
) {

    @Provides
    @FragmentScope
    fun providesAdapter(): FragFriendListAdapter = FragFriendListAdapter()

    @Provides
    @FragmentScope
    fun providesLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(frag.activity, LinearLayoutManager.VERTICAL, false)

    @Provides
    @FragmentScope
    fun providesPresenter(
            xmppManager: IXmppManager,
            friendListManager: FragFriendListManager
    ): FragFriendListPresenter
            = FragFriendListPresenter(
            xmppManager, friendListManager)

    @Provides
    @FragmentScope
    fun providesFriendListManager(app: Application, adapter: FragFriendListAdapter)
        = FragFriendListManager(app, adapter)
}