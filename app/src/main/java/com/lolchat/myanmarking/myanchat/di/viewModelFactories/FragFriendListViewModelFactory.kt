@file:Suppress("UNCHECKED_CAST")

package com.lolchat.myanmarking.myanchat.di.viewModelFactories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.FragFriendListViewModel

class FragFriendListViewModelFactory(
        val xmppManager: IXmppManager
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(p0: Class<T>): T {
        return FragFriendListViewModel(xmppManager) as T
    }

}