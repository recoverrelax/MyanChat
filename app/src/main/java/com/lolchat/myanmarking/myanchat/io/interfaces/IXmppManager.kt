package com.lolchat.myanmarking.myanchat.io.interfaces

import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity

interface IXmppManager{
    fun addOnFriendChangeListener(listener: IFriendChangeListener)
    fun removeOnFriendChangeListener(listener: IFriendChangeListener)
    fun requestFreshData()
    fun getUpdatedFriendEntityByName(friendName: String): FriendEntity?
}