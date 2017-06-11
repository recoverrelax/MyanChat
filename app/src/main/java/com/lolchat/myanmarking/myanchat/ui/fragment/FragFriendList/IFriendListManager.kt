package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import android.support.v7.util.DiffUtil
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity

interface IFriendListManager{
    fun onFreshData(friendEntityList: List<FriendEntity>)
    fun onDataChanged(result: DiffUtil.DiffResult?, friendEntityList: List<FriendEntity>)
}