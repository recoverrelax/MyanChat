package com.lolchat.myanmarking.myanchat.io.interfaces

import com.lolchat.myanmarking.myanchat.io.model.xmpp.Friend
import io.reactivex.Observable

interface IXmppManager{
    fun getFriendListName(): Observable<List<Friend>>

    fun addOnFriendChangeListener(listener: IFriendChangeListener)
    fun removeOnFriendChangeListener(listener: IFriendChangeListener)
}