package com.lolchat.myanmarking.myanchat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lolchat.myanmarking.myanchat.io.model.item_view.FriendViewModel
import com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces.IFriendViewModel
import com.lolchat.myanmarking.myanchat.io.model.xmpp.Friend
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.ui.view_item.FriendView

class FragFriendListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var friendList: List<Friend> = emptyList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as FriendListViewHolder
        holder.setItem(getItem(position))
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FriendListViewHolder(FriendView(parent.context, null, 0, 0))
    }

    fun setAdapterItems(friendList: List<Friend>){
        this.friendList = friendList
        notifyDataSetChanged()
    }

    private fun getItem(adapterPosition: Int):Friend{
        return friendList[adapterPosition]
    }

    inner class FriendListViewHolder(itemView: FriendView): RecyclerView.ViewHolder(itemView) {
        fun setItem(item: Friend){
            itemView as IFriendViewModel
            itemView.setItem(
                    FriendViewModel(
                            name = item.name,
                            sumIconUrl = EMPTY_STRING,
                            statusMessage = item.getStatusMessage(),
                            presenceStatus = "Chatting"
                    )
            )
        }
    }
}