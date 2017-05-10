package com.lolchat.myanmarking.myanchat.ui.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lolchat.myanmarking.myanchat.io.interfaces.IFriendChangeListener
import com.lolchat.myanmarking.myanchat.io.interfaces.IRecycleableView
import com.lolchat.myanmarking.myanchat.io.model.item_view.FriendViewModel
import com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces.IFriendViewModel
import com.lolchat.myanmarking.myanchat.io.model.xmpp.Friend
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.ui.view_item.FriendView
import timber.log.Timber

class FragFriendListAdapter(
        val act: Activity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IFriendChangeListener {
    private var friendList: MutableList<Friend> = mutableListOf()
    private val INFO_LOG = true

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as FriendListViewHolder
        holder.setItem(getItem(position))
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FriendListViewHolder(FriendView(parent.context, null, 0))
    }

    fun setAdapterItems(friendList: List<Friend>) {
        this.friendList = friendList.toMutableList()
        notifyDataSetChanged()
    }

    private fun getItem(adapterPosition: Int): Friend {
        return friendList[adapterPosition]
    }


    inner class FriendListViewHolder(itemView: FriendView) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: Friend) {
            itemView as IFriendViewModel
            itemView.setItem(
                    FriendViewModel(
                            name = item.name,
                            sumIconUrl = item.profileIcon,
                            statusMessage = item.statusMessage,
                            presenceStatus = "Chatting",
                            isFriendOnline = item.isOnline,
                            gameStatus = item.gameStatus
                    )
            )
        }
    }

    override fun onFriendsRemoved(friends: MutableList<Friend>) {
        act.runOnUiThread {
            for (pos in friends.indices) {
                val friend = friends[pos]
                if (INFO_LOG) Timber.i("onFriendsRemoved: ${friend.name}")
                friendList.remove(friend)
                notifyItemRemoved(pos)
            }
        }
    }

    override fun onFriendsAdded(friends: MutableList<Friend>) {
        act.runOnUiThread {
            for (pos in friends.indices) {
                val friend = friends[pos]
                if (INFO_LOG) Timber.i("onFriendsAdded: ${friend.name}")
                friendList.add(friend)
                notifyItemInserted(pos)
            }
        }
    }

    override fun onFriendsChanged(friends: MutableList<Friend>) {
        act.runOnUiThread {
            @Suppress("LoopToCallChain")
            for (pos in friends.indices) {
                val friend = friends[pos]
                friendList.removeAt(pos)
                friendList.add(pos, friend)
                notifyItemChanged(pos)
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder.itemView as? IRecycleableView)?.onRecycle()
    }
}