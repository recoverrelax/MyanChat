package com.lolchat.myanmarking.myanchat.di.component

import com.lolchat.myanmarking.myanchat.di.module.FragFriendListModule
import com.lolchat.myanmarking.myanchat.di.scopes.FragmentScope
import com.lolchat.myanmarking.myanchat.ui.FragFriendList
import dagger.Component

@FragmentScope
@Component(
        modules = arrayOf(FragFriendListModule::class),
        dependencies = arrayOf(MyAppComponent::class)
)
interface FragFriendListComponent{
    fun inject(frag: FragFriendList)
}