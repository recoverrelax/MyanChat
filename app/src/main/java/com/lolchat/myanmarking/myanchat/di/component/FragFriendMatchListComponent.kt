package com.lolchat.myanmarking.myanchat.di.component

import com.lolchat.myanmarking.myanchat.di.scopes.FragmentScope
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendList
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList.FragFriendMatchList
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = arrayOf(FragFriendListComponent.FragFriendListModule::class))
interface FragFriendMatchListComponent: AndroidInjector<FragFriendList>{

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FragFriendList>(){
        /*abstract fun fragFriendListModule(app: FragFriendListModule)

        override fun seedInstance(frag: FragFriendList) {
            fragFriendListModule(FragFriendListModule(frag))
        }*/
    }

    @Module
    class FragFriendListModule(
            val frag: FragFriendMatchList
    )
}