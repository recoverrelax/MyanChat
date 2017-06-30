package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList

import com.lolchat.myanmarking.myanchat.base.mvp.BaseViewStates
import com.lolchat.myanmarking.myanchat.io.model.xmpp.SkinInfo

sealed class FragFriendMatchListViewStates: BaseViewStates{
    class LoadBasicInfo(
            val isUserOnline: Boolean,
            val profileIconUrl: String,
            val skinInfo: SkinInfo?
    ): FragFriendMatchListViewStates()
}