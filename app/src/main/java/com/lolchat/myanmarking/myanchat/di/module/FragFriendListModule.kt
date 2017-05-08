package com.lolchat.myanmarking.myanchat.di.module

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lolchat.myanmarking.myanchat.di.scopes.FragmentScope
import com.lolchat.myanmarking.myanchat.ui.adapter.FragFriendListAdapter
import dagger.Module
import dagger.Provides

@Module
class FragFriendListModule(
        val act: Activity
) {

    @Provides
    @FragmentScope
    fun providesAdapter(): FragFriendListAdapter = FragFriendListAdapter()

    @Provides
    @FragmentScope
    fun providesLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false)
}