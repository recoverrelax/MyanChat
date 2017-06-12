package com.lolchat.myanmarking.myanchat.di.module

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lolchat.myanmarking.myanchat.di.scopes.FragmentScope
import com.lolchat.myanmarking.myanchat.di.viewModelFactories.FragFriendListViewModelFactory
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.ui.adapter.FragFriendListAdapter
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendList
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendListViewModel
import dagger.Module
import dagger.Provides

@Module
class FragFriendListModule(
        val frag: FragFriendList
) {

    @Provides
    @FragmentScope
    fun providesViewModel(
            viewModelFactory: FragFriendListViewModelFactory
    ): FragFriendListViewModel {
        return ViewModelProviders.of(frag, viewModelFactory)
                .get(FragFriendListViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun providesAdapter(): FragFriendListAdapter = FragFriendListAdapter()

    @Provides
    @FragmentScope
    fun providesLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(frag.activity, LinearLayoutManager.VERTICAL, false)

    @Provides
    @FragmentScope
    fun providesViewModelFactory(
            xmppManager: IXmppManager
    ): FragFriendListViewModelFactory
            = FragFriendListViewModelFactory(xmppManager)
}