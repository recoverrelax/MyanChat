package com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces

import com.lolchat.myanmarking.myanchat.io.interfaces.ICollapsable
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity

interface IFriendEntity : ICollapsable{
    fun setItem(viewModel: FriendEntity)
}