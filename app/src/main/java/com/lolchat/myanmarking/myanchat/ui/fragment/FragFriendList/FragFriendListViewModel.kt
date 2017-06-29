package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import com.lolchat.myanmarking.myanchat.base.mvp.BaseViewModel
import com.lolchat.myanmarking.myanchat.io.interfaces.IFriendChangeListener
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.ui.adapter.OnFriendLongClickListener

class FragFriendListViewModel(
        val xmppManager: IXmppManager
): BaseViewModel<FragFriendListViewStates>(), IFriendChangeListener {

    fun addOnFriendChangeListener() = xmppManager.addOnFriendChangeListener(this)
    fun removeOnFriendChangeListener() = xmppManager.removeOnFriendChangeListener(this)

    init {
        addOnFriendChangeListener()
    }

    override fun onDataChanged(isFreshData: Boolean, friendEntityList: List<FriendEntity>) {
        // we sort the list every time it is send to the adapter. This way, a new list is always provided.
        // which it's also sorted.
        val copyList = friendEntityList.sortFriendEntity()


        if (isFreshData) {
            postEvent(FragFriendListViewStates.OnFreshData(copyList))
        } else {
            postEvent(FragFriendListViewStates.OnChangedData(copyList))
        }
    }

    private fun List<FriendEntity>.sortFriendEntity(): List<FriendEntity> {
        return this.sortedWith(
                compareBy(
                        { it.isFriendOnline },
                        { it.presenceMode },
                        { it.gameStatus },
                        { it.name }
                )
        ).reversed()
    }

    override fun onCleared() {
        removeOnFriendChangeListener()
        super.onCleared()
    }

    fun onResume() {
        postEvent(FragFriendListViewStates.Loading)
        xmppManager.requestFreshData()
    }
}