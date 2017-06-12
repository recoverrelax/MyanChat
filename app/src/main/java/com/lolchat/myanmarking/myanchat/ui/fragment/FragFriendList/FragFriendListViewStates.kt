package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import com.lolchat.myanmarking.myanchat.base.mvp.BaseViewStates
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity

sealed class FragFriendListViewStates(
        val isLoading: Boolean
): BaseViewStates{
    object Loading: FragFriendListViewStates(true)
    class OnFreshData(val list: List<FriendEntity>): FragFriendListViewStates(false)
    class OnChangedData(val list: List<FriendEntity>): FragFriendListViewStates(false)
    object OnNoData: FragFriendListViewStates(false)
}