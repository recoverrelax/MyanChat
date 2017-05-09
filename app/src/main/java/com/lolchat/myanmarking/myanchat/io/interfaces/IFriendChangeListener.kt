package com.lolchat.myanmarking.myanchat.io.interfaces

import com.lolchat.myanmarking.myanchat.io.model.xmpp.Friend

interface IFriendChangeListener {
    fun onFriendsRemoved(friends: MutableList<Friend>)
    fun onFriendsAdded(friends: MutableList<Friend>)
    fun onFriendsChanged(friends: MutableList<Friend>)
}