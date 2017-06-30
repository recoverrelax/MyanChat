package com.lolchat.myanmarking.myanchat.di.module

import android.support.v4.app.Fragment
import com.lolchat.myanmarking.myanchat.di.component.FragFriendListComponent
import com.lolchat.myanmarking.myanchat.di.component.FragFriendMatchListComponent
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendList
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList.FragFriendMatchList
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(FragFriendListComponent::class, FragFriendMatchListComponent::class))
abstract class FragmentBindModule {
    @Binds
    @IntoMap
    @FragmentKey(FragFriendList::class)
    internal abstract fun bindFragFriendList(builder: FragFriendListComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(FragFriendMatchList::class)
    internal abstract fun bindFragFriendMatchList(builder: FragFriendMatchListComponent.Builder): AndroidInjector.Factory<out Fragment>
}
