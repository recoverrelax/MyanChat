package com.lolchat.myanmarking.myanchat.io.interfaces

import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity

interface IFriendChangeListener {
    fun onDataChanged(isFreshData: Boolean, friendEntityList: List<FriendEntity>)
}