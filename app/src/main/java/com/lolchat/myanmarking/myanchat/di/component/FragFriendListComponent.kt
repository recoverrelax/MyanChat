package com.lolchat.myanmarking.myanchat.di.component

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
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
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = arrayOf(FragFriendListComponent.FragFriendListModule::class))
interface FragFriendListComponent: AndroidInjector<FragFriendList>{

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FragFriendList>(){
        abstract fun fragFriendListModule(app: FragFriendListModule)

        override fun seedInstance(frag: FragFriendList) {
            fragFriendListModule(FragFriendListModule(frag))
        }
    }

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
        fun providesAdapter(): FragFriendListAdapter = FragFriendListAdapter(frag)

        @Provides
        @FragmentScope
        fun providesLayoutManager(act: AppCompatActivity): RecyclerView.LayoutManager = LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false)

        @Provides
        @FragmentScope
        fun providesViewModelFactory(
                xmppManager: IXmppManager
        ): FragFriendListViewModelFactory
                = FragFriendListViewModelFactory(xmppManager)
    }

}