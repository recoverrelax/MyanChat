package com.lolchat.myanmarking.myanchat.di.component

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import com.lolchat.myanmarking.myanchat.di.scopes.FragmentScope
import com.lolchat.myanmarking.myanchat.di.viewModelFactories.FragFriendMatchListViewModelFactory
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.ui.activity.MainActivity
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList.FragFriendMatchList
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList.FragFriendMatchListViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = arrayOf(FragFriendMatchListComponent.FragFriendListMatchModule::class))
interface FragFriendMatchListComponent: AndroidInjector<FragFriendMatchList>{

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FragFriendMatchList>(){
        abstract fun fragFriendListModule(app: FragFriendListMatchModule)

        override fun seedInstance(frag: FragFriendMatchList) {
            fragFriendListModule(FragFriendListMatchModule(frag))
        }
    }

    @Module
    class FragFriendListMatchModule(
            val frag: FragFriendMatchList
    ){

        @Provides
        @FragmentScope
        fun providesViewModel(
                viewModelFactory: FragFriendMatchListViewModelFactory
        ): FragFriendMatchListViewModel {
            return ViewModelProviders.of(frag, viewModelFactory)
                    .get(FragFriendMatchListViewModel::class.java)
        }

        @Provides
        @FragmentScope
        fun providesViewModelFactory(xmppManager: IXmppManager, roomInteractor: RoomInteractor): FragFriendMatchListViewModelFactory
                = FragFriendMatchListViewModelFactory(xmppManager, roomInteractor)
    }
}