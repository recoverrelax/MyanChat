package com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList

import android.app.Application
import android.support.v7.util.DiffUtil
import com.lolchat.myanmarking.myanchat.io.interfaces.IFriendChangeListener
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.other.util.assertNotMainThread
import org.jetbrains.anko.runOnUiThread

class FragFriendListManager(
        val app: Application,
        val listener: IFriendListManager
) : IFriendChangeListener {

    private var friendEntityList: List<FriendEntity> = emptyList()

    override fun onDataChanged(isFreshData: Boolean, friendEntityList: List<FriendEntity>) {
        val newList = friendEntityList.copy().sortFriendEntity()
        if (isFreshData) {
            app.runOnUiThread {
                listener.onFreshData(newList)
            }
            this.friendEntityList = newList
        } else {
            /*val diffCb = object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
                        = friendEntityList[oldItemPosition].name == newList[newItemPosition].name

                override fun getOldListSize(): Int = friendEntityList.size
                override fun getNewListSize(): Int = newList.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
                        = friendEntityList[oldItemPosition].sameAs(newList[newItemPosition])
            }
            val diffResult = DiffUtil.calculateDiff(diffCb)*/
            app.runOnUiThread {
                listener.onDataChanged(null, newList)
            }
            this.friendEntityList = newList
        }
    }

    private fun List<FriendEntity>.copy(): List<FriendEntity> {
        val list = mutableListOf<FriendEntity>()

        this.forEach {
            list.add(it.copy())
        }
        return list
    }

    private fun List<FriendEntity>.sortFriendEntity(): List<FriendEntity> {
        return this.sortedWith(
                compareBy(
                        { !it.isFriendOnline },
                        { it.presenceMode },
                        { it.gameStatus },
                        { it.name }
                )
        )
    }
}