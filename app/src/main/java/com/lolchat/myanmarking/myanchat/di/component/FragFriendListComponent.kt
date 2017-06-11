package com.lolchat.myanmarking.myanchat.di.component

import com.lolchat.myanmarking.myanchat.di.module.FragFriendListModule
import com.lolchat.myanmarking.myanchat.di.scopes.FragmentScope
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomPersistentDb
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendList
import dagger.Component

@FragmentScope
@Component(
        modules = arrayOf(FragFriendListModule::class),
        dependencies = arrayOf(MyAppComponent::class)
)
interface FragFriendListComponent{
    fun inject(frag: FragFriendList)


}