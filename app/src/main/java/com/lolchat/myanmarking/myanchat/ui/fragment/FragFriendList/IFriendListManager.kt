package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity

interface IFriendListManager{
    fun onFreshData(friendEntityList: List<FriendEntity>)
    fun onDataChanged(friendEntityList: List<FriendEntity>)
}