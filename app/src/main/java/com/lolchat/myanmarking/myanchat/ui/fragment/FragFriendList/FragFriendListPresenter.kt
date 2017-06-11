package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import com.lolchat.myanmarking.myanchat.base.mvp.BasePresenter
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager

class FragFriendListPresenter(
    val xmppManager: IXmppManager,
    val friendListManager: FragFriendListManager
): BasePresenter<IFragFriendListView>() {

    fun addOnFriendChangeListener() = xmppManager.addOnFriendChangeListener(friendListManager)
    fun removeOnFriendChangeListener() = xmppManager.removeOnFriendChangeListener(friendListManager)

    override fun release() {
    }
}