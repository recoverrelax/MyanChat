package com.lolchat.myanmarking.myanchat.io.interfaces

interface IXmppManager{
    fun addOnFriendChangeListener(listener: IFriendChangeListener)
    fun removeOnFriendChangeListener(listener: IFriendChangeListener)
    fun requestFreshData()
}