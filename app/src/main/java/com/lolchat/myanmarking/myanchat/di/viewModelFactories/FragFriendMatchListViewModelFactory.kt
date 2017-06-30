@file:Suppress("UNCHECKED_CAST")

package com.lolchat.myanmarking.myanchat.di.viewModelFactories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendListViewModel
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendMatchList.FragFriendMatchListViewModel

class FragFriendMatchListViewModelFactory(
        val xmppManager: IXmppManager,
        val roomInteractor: RoomInteractor
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(p0: Class<T>): T {
        return FragFriendMatchListViewModel(xmppManager, roomInteractor) as T
    }

}